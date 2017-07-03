package SplitFocusTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import Shared.ImageModule;
import Shared.Layer;
import Shared.Window;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class TestLayer extends Layer implements KeyListener, MouseMotionListener, TuioListener {
	
	//VARIABLES
	
	//Fen�tres
	protected int nbWindow; //Nombre de fen�tres actives
	protected int previous; //Nombre de fen�tres actives au dernier arrangement
	protected ArrayList<Window> windows; //Liste des fen�tres
	
	//Couleurs des fen�tres
	protected Color[] tempColor = {new Color(255, 156, 0), new Color(0, 111, 225), new Color(66, 166, 0),
									new Color(128, 0, 160), new Color(255, 255, 0), new Color(0, 240, 255),
									new Color(153, 204, 0), new Color(189, 0, 236), new Color(148, 148, 148)};
	protected ArrayList<Color> colors; //Liste des couleurs de fen�tres
	
	//Arrangements de fen�tres
	protected int alt; //Arrangement de fen�tres
	protected boolean lastAlt; //True si alt est le dernier arrangement possible pour le nombre de fen�tres courantes
	
	//Modules images
	protected ArrayList<ImageModule> modules; //Liste des modules images
	
	//Entr�es souris/tactiles/clavier
	protected boolean movesMouse, movesTUIO; //True si la souris/le tactile est en mouvement
	protected String control; //D�finit quel �cran a le contr�le (SplitFocus ou MainView)
	
	//CONSTRUCTEURS
	public TestLayer(int nbWindow, Dimension d){
		super(d);
		this.nbWindow = nbWindow;
		setup();
	}
	
	public TestLayer(int nbWindow, int x, int y, Dimension d){
		super(x, y, d);
		this.nbWindow = nbWindow;
		setup();
	}
	
	//Fonction commune aux constructeurs
	public void setup(){
		
		//Cr�ation de la liste de couleurs des fen�tres
		this.colors = new ArrayList<Color>();
		this.colors.addAll(Arrays.asList(tempColor));
		
		//D�finition de l'arrangement de d�part
		this.alt = 0;
		this.previous = nbWindow;
		this.lastAlt = false;
		
		//D�finition de la mobilit�
		this.movesMouse = false;
		this.movesTUIO = false;
		
		//D�finition des fen�tres
		this.windows = new ArrayList<Window>();
		for(int i = 0; i < 9; i++){
			this.windows.add(new Window(i, 0, 0, 0, 0, colors.get(i), 2, 1.f));
		}
		
		//Ouverture des fichiers images
		File f = new File(System.getProperty("user.dir")+"/images2");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		
		//D�finition des modules images
		this.modules = new ArrayList<ImageModule>();
		System.out.print("Lecture des fichiers image...");
		for(int i = 0; i < 9; i++){
			try{
				BufferedImage bim = ImageIO.read(files.get(i));
				ImageModule mod = new ImageModule(bim);
				this.modules.add(mod);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		System.out.println(" Termin�.");
		
		//Mise en place de l'arrangement initial
		setAreaPlacement();
	}
	
	//M�THODES - G�N�RAL
	//V�rifie s'il y a un mouvement sur la fen�tre
	public boolean checkIfMoves(){
		return(this.movesMouse || this.movesTUIO);
	}
	
	//D�placement d'une fen�tre
	//Prend en entr�e la position (x,y) de la souris, la fen�tre courante et son image
	public void move(int x, int y, Window w, ImageModule img){
		//Si la fen�tre est active, on la d�place
		if(w.getActive()){
			w.setStartX(w.getLastStartX()-(x-w.getContactX()));
			w.setStartY(w.getLastStartY()-(y-w.getContactY()));
		}
		
		//Si on d�place la fen�tre en dehors des limites de l'image, on bloque le d�placement
		//D�passement � droite
		if(w.getStartX() > img.getWidth()-w.getWidth()){
			w.setStartX(img.getWidth()-w.getWidth());
		}
		
		//D�passement � gauche
		if(w.getStartX() < 0){
			w.setStartX(0);
		}
		
		//D�passement en bas
		if(w.getStartY() > img.getHeight()-w.getHeight()){
			w.setStartY(img.getHeight()-w.getHeight());
		}
		
		//D�passement en haut
		if(w.getStartY() < 0){
			w.setStartY(0);
		}
	}
	
	//Placement des fen�tres
	public void setAreaPlacement(){
		
		lastAlt = false;
		
		if(nbWindow == 1){
			windows.get(0).setPosition(x+5, y+5, width-10, height-10);
		}
		
		if(nbWindow == 2){
			if(alt == 0){
				windows.get(0).setPosition(x+5, y+5, (width-20)/2, height-10);
				windows.get(1).setPosition(x+5+width/2, y+5, (width-20)/2, height-10);
			}
			else{
				windows.get(0).setPosition(x+5, y+5, width-10, (height-20)/2);
				windows.get(1).setPosition(x+5, y+5+height/2, width-10, (height-20)/2);
				lastAlt = true;
			}
		}
		
		if(nbWindow == 3){
			if(alt == 0){
				for(int i = 0; i < 3; i++){
					windows.get(i).setPosition(x+5+i*width/3, y+5, (width-30)/3, height-10);
				}
			}
			else if(alt == 1){
				for(int i = 0; i < 3; i++){
					windows.get(i).setPosition(x+5, y+5+i*height/3,	width-10, (height-30)/3);
				}
			}
			else if(alt == 2){
				windows.get(0).setPosition(x+5, y+5, (width-20)/2, height-10);
				windows.get(1).setPosition(x+5+width/2, y+5, (width-20)/2, (height-20)/2);
				windows.get(2).setPosition(x+5+width/2, y+5+height/2, (width-20)/2, (height-20)/2);
			}
			else if(alt == 3){
				windows.get(0).setPosition(x+5, y+5, width-10, (height-20)/2);
				windows.get(1).setPosition(x+5, y+5+height/2, (width-20)/2, (height-20)/2);
				windows.get(2).setPosition(x+5+width/2, y+5+height/2, (width-20)/2, (height-20)/2);
			}
			else if(alt == 4){
				windows.get(0).setPosition(x+5+width/2, y+5, (width-20)/2, height-10);
				windows.get(1).setPosition(x+5, y+5, (width-20)/2, (height-20)/2);
				windows.get(2).setPosition(x+5, y+5+height/2, (width-20)/2, (height-20)/2);
			}
			else if(alt == 5){
				windows.get(0).setPosition(x+5, y+5+height/2, width-10, (height-20)/2);
				windows.get(1).setPosition(x+5, y+5, (width-20)/2, (height-20)/2);
				windows.get(2).setPosition(x+5+width/2, y+5, (width-20)/2, (height-20)/2);
			}
			else if(alt == 6){
				windows.get(0).setPosition(x+5, y+5+height/3, width-10, 2*(height-15)/3);
				windows.get(1).setPosition(x+5, y+5, (width-30)/3, (height-30)/3);
				windows.get(2).setPosition(x+5+width/3, y+5, 2*(width-15)/3, (height-30)/3);
				lastAlt = true;
			}
		}
		
		if(nbWindow == 4){
			if(alt == 0){
				for(int i = 0; i < 4; i++){
					windows.get(i).setPosition(x+5+(i*width/2)%width, y+5+(int)(i/2)*height/2, (width-20)/2, (height-20)/2);
				}
			}
			else if(alt == 1){
				windows.get(0).setPosition(x+5, y+5, (width-20)/2, height-10);
				windows.get(1).setPosition(x+5+width/2, y+5, (width-20)/2, (height-30)/3);
				windows.get(2).setPosition(x+5+width/2, y+5+height/3, (width-20)/2, (height-30)/3);
				windows.get(3).setPosition(x+5+width/2, y+5+2*height/3, (width-20)/2, (height-30)/3);
			}
			else if(alt == 2){
				windows.get(0).setPosition(x+5, y+5, width-10, (height-20)/2);
				windows.get(1).setPosition(x+5, y+5+height/2, (width-30)/3, (height-20)/2);
				windows.get(2).setPosition(x+5+width/3, y+5+height/2, (width-30)/3, (height-20)/2);
				windows.get(3).setPosition(x+5+2*width/3, y+5+height/2, (width-30)/3, (height-20)/2);
			}
			else if(alt == 3){
				windows.get(0).setPosition(x+5+width/2, y+5, (width-20)/2, height-10);
				windows.get(1).setPosition(x+5, y+5, (width-20)/2, (height-30)/3);
				windows.get(2).setPosition(x+5, y+5+height/3, (width-20)/2, (height-30)/3);
				windows.get(3).setPosition(x+5, y+5+2*height/3, (width-20)/2, (height-30)/3);
			}
			else if(alt == 4){
				windows.get(0).setPosition(x+5, y+5+height/2, width-10, (height-20)/2);
				windows.get(1).setPosition(x+5, y+5, (width-30)/3, (height-20)/2);
				windows.get(2).setPosition(x+5+width/3, y+5, (width-30)/3, (height-20)/2);
				windows.get(3).setPosition(x+5+2*width/3, y+5, (width-30)/3, (height-20)/2);
			}
			else if(alt == 5){
				windows.get(0).setPosition(x+5, y+5+height/3, width-10, 2*(height-15)/3);
				windows.get(1).setPosition(x+5, y+5, (width-30)/3, (height-30)/3);
				windows.get(2).setPosition(x+5+width/3, y+5, (width-30)/3, (height-30)/3);
				windows.get(3).setPosition(x+5+2*width/3, y+5, (width-30)/3, (height-30)/3);
			}
			else if(alt == 6){
				windows.get(0).setPosition(x+5+width/3, y+5+height/3, 2*(width-15)/3, 2*(height-15)/3);
				windows.get(1).setPosition(x+5, y+5, (width-30)/3, (height-30)/3);
				windows.get(2).setPosition(x+5+width/3, y+5, 2*(width-15)/3, (height-30)/3);
				windows.get(3).setPosition(x+5, y+5+height/3, (width-30)/3, 2*(height-15)/3);
				lastAlt = true;
			}
		}
		
		if(nbWindow == 5){
			if(alt == 0){
				for(int i = 0; i < 5; i++){
					if(i < 3) windows.get(i).setPosition(x+5+i*width/3, y+5+(int)(i/3)*height/2, (width-30)/3, (height-20)/2);
					else windows.get(i).setPosition(x+5+((i-1)*width/2)%width, y+5+(int)((i-1)/2)*height/2, (width-20)/2, (height-20)/2);
				}
			}
			else if(alt == 1){
				for(int i = 0; i < 5; i++){
					if(i < 2) windows.get(i).setPosition(x+5+i*width/2, y+5+(int)(i/2)*height/2, (width-20)/2, (height-20)/2);
					else windows.get(i).setPosition(x+5+((i+1)*width/3)%width, y+5+(int)((i+1)/3)*height/2, (width-30)/3, (height-20)/2);
				}
			}
			else if(alt == 2){
				windows.get(0).setPosition(x+5, y+5, (width-20)/2, height-10);
				windows.get(1).setPosition(x+5+width/2, y+5, (width-40)/4, (height-20)/2);
				windows.get(2).setPosition(x+5+3*width/4, y+5, (width-40)/4, (height-20)/2);
				windows.get(3).setPosition(x+5+width/2, y+5+height/2, (width-40)/4, (height-20)/2);
				windows.get(4).setPosition(x+5+3*width/4, y+5+height/2, (width-40)/4, (height-20)/2);
			}
			else if(alt == 3){
				windows.get(0).setPosition(x+5+width/2, y+5, (width-20)/2, height-10);
				windows.get(1).setPosition(x+5, y+5, (width-40)/4, (height-20)/2);
				windows.get(2).setPosition(x+5+width/4, y+5, (width-40)/4, (height-20)/2);
				windows.get(3).setPosition(x+5, y+5+height/2, (width-40)/4, (height-20)/2);
				windows.get(4).setPosition(x+5+width/4, y+5+height/2, (width-40)/4, (height-20)/2);
				lastAlt = true;
			}
		}
		
		if(nbWindow == 6){
			for(int i = 0; i < 6; i++){
				windows.get(i).setPosition(x+5+i*(width/3)%width, y+5+(int)(i/3)*(height/2), (width-30)/3, (height-20)/2);
			}
			lastAlt = true;
		}
		
		if(nbWindow == 7){
			for(int i = 0; i < 7; i++){
				if(alt == 0){
					if(i < 4) windows.get(i).setPosition(x+5+i*width/4, y+5+(int)(i/4)*height/2, (width-40)/4, (height-20)/2);
					else windows.get(i).setPosition(x+5+((i-1)*width/3)%width, y+5+(int)((i-1)/3)*height/2, (width-30)/3, (height-20)/2);
				}
				else{
					if(i < 3) windows.get(i).setPosition(x+5+i*width/3, y+5+(int)(i/3)*height/2, (width-30)/3, (height-20)/2);
					else windows.get(i).setPosition(x+5+((i+1)*width/4)%width, y+5+(int)((i+1)/4)*height/2, (width-40)/4, (height-20)/2);			
					lastAlt = true;
				}
			}
		}
		
		if(nbWindow == 8){
			for(int i = 0; i < 8; i++){
				windows.get(i).setPosition(x+5+(i*width/4)%width, y+5+(int)(i/4)*height/2, (width-40)/4, (height-20)/2);
			}
			lastAlt = true;
		}
		
		if(nbWindow == 9){
			for(int i = 0; i < nbWindow; i++){
				windows.get(i).setPosition(x+5+(i*width/3)%width, y+5+(int)(i/3)*height/3, (width-30)/3, (height-30)/3);
			}
			lastAlt = true;
		}
		
		for(int i = nbWindow; i < 9; i++){
			windows.get(i).setPosition(0, 0, 0, 0);
		}
		
	}
	
	//M�THODES - AFFICHAGE
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		for(int i = 0; i < nbWindow; i++){
			int mID = windows.get(i).getModuleID();
			modules.get(mID).paintComponent(g2d, windows.get(i));
			windows.get(i).paintComponent(g2d);
			if(control == "SplitFocus"){
				move((int)mouse.getX(), (int)mouse.getY(), windows.get(i), modules.get(mID));
			}
		}
	}
	
	//M�THODES - GETTERS/SETTERS
	
	//Fen�tres
	public ArrayList<Window> getWindows(){
		return(windows);
	}

	public void setWindows(ArrayList<Window> windows){
		this.windows = windows;
	}
	
	public void setControl(String control){
		this.control = control;
	}
	
	//M�THODES - ENTR�ES SOURIS/CLAVIER/TUIO
	
	//Touche press�e
	public void keyPressed(KeyEvent ke) {
		//Si la touche est une touche du pav� num�rique entre 
		//1 et 9, on modifie l'arrangement des fen�tres
		if(ke.getKeyCode() > 96 && ke.getKeyCode() < 106){
			this.nbWindow = ke.getKeyCode()-96;
			if(nbWindow == previous && !lastAlt) alt++;
			else alt = 0;
			this.previous = ke.getKeyCode()-96;
			setAreaPlacement();
		}
	}
	
	//Tactile press�
	public void addTuioCursor(TuioCursor tc) {
		//Si le point de contact est le premier, on passe movesTUIO � true., 
		if(tc.getCursorID() == 0){
			this.movesTUIO = true;
		}
	}
	
	//Tactile retir�
	public void removeTuioCursor(TuioCursor tc) {
		//Si le point de contact est le premier, on passe movesTUIO � false.
		if(tc.getCursorID() == 0){
			this.movesTUIO = false;
		}
	}
	
	//Souris press�e
	public void mousePressed(MouseEvent e) {
		//On passe movesMouse � true, puis on actualise la fen�tre courante.
		this.movesMouse = true;
		for(Window w : windows){
			w.isInside(e.getX(), e.getY());
		}
	}
	
	//Souris rel�ch�e
	public void mouseReleased(MouseEvent e) {
		//On passe movesMouse � true, puis on d�finit toutes les fen�tres inactives.
		this.movesMouse = false;
		for(Window w:windows){
			w.setInactive();
			w.updateCorner();
		}
	}

	//Fonctions non-utilis�es forc�es par l'interfaces
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void addTuioBlob(TuioBlob t) {}
	public void addTuioObject(TuioObject t) {}
	public void refresh(TuioTime t) {}
	public void removeTuioBlob(TuioBlob t) {}
	public void removeTuioObject(TuioObject t) {}
	public void updateTuioBlob(TuioBlob t) {}
	public void updateTuioCursor(TuioCursor t) {}
	public void updateTuioObject(TuioObject t) {}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	
}
