package MainViewTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import Shared.Layer;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class TestLayer extends Layer implements KeyListener, MouseListener, TuioListener {
	
	protected int nbZones;
	protected Color[] tempColor = {new Color(255, 156, 0), new Color(0, 111, 225), new Color(66, 166, 0),
									new Color(128, 0, 160), new Color(255, 255, 0), new Color(0, 240, 255),
									new Color(153, 204, 0), new Color(189, 0, 236), new Color(148, 148, 148)};
	protected ArrayList<Window> windows;
	protected ArrayList<Color> colors;
	protected ArrayList<ImageModule> modules;
	protected ArrayList<WindowMainView> windowsMV;
	protected ImageMainView currentImage;
	protected int alt; //Définit le numéro de l'arrangement pour un nombre de fenêtres données 
	protected int previous;
	protected boolean lastAlt, movesMouse, movesTUIO;
	
	public TestLayer(int nbZones, Dimension d){
		super(d);
		this.nbZones = nbZones;
		setup();
	}
	
	public TestLayer(int nbZones, int x, int y, Dimension d){
		super(x, y, d);
		this.nbZones = nbZones;
		setup();
	}
	
	public void setup(){
		//Ajout des couleurs des fenêtres
		this.colors = new ArrayList<Color>();
		this.colors.addAll(Arrays.asList(tempColor));
		
		//Définition de l'arrangement de base
		this.alt = 0;
		this.lastAlt = false;
		
		//Enregistre le nombre de fenêtres dans la précédente configuration
		this.previous = nbZones;
		
		//Initialise la liste des fenêtres
		this.windows = new ArrayList<Window>();
		this.windowsMV = new ArrayList<WindowMainView>();
		
		//Assigne une couleur à chaucne des fenêtres
		for(int i = 0; i < 9; i++){
			this.windows.add(new Window(i, 0, 0, 0, 0, colors.get(i)));
			this.windowsMV.add(new WindowMainView(i, 0, 0, 0, 0, 1.f, colors.get(i)));
		}
		
		//Ouvre les fichiers image, crée les modules et les assigne aux fenêtres
		File f = new File(System.getProperty("user.dir")+"/images2");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		this.modules = new ArrayList<ImageModule>();
		System.out.print("Lecture des fichiers image...");
		for(int i = 0; i < 9; i++){
			try{
				BufferedImage bim = ImageIO.read(files.get(i));
				ImageModule mod = new ImageModule(bim, windows.get(i));
				this.modules.add(mod);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		System.out.println(" Terminé.");
		
		setAreaPlacement();
		
		this.movesMouse = false;
		this.movesTUIO = false;
		
		//Définit l'image à l'ouverture
		this.currentImage = new ImageMainView(modules.get(0));
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		//Affichage de l'image en cours
		currentImage.paintComponent(g2d);
		
		float ratio = currentImage.getRatio();
		
		for(ImageModule im : modules){
			if(im.getImage().equals(currentImage.getImage())){
				int x = im.getStartX();
				int y = im.getStartY();
				int id = im.getWindow().getID();
				int w = windows.get(id).getWidth();
				int h = windows.get(id).getHeight();
				WindowMainView wmv = windowsMV.get(id);
				wmv.updateRatio(ratio);
				wmv.updatePosition((int)(x*ratio)+currentImage.getStartX(), (int)(y*ratio)+currentImage.getStartY());
				wmv.updateSize((int)(w*ratio), (int)(h*ratio));
				wmv.paintComponent(g2d);
			}
		}
	}
	
	public boolean moves(){
		return(this.movesMouse || this.movesTUIO);
	}
	
	public void setAreaPlacement(){
		
		lastAlt = false;
		
		if(nbZones == 1){
			windows.get(0).setPosition(x+5, y+5, width-10, height-10);
		}
		
		if(nbZones == 2){
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
		
		if(nbZones == 3){
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
		
		if(nbZones == 4){
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
		
		if(nbZones == 5){
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
		
		if(nbZones == 6){
			for(int i = 0; i < 6; i++){
				windows.get(i).setPosition(x+5+i*(width/3)%width, y+5+(int)(i/3)*(height/2), (width-30)/3, (height-20)/2);
			}
			lastAlt = true;
		}
		
		if(nbZones == 7){
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
		
		if(nbZones == 8){
			for(int i = 0; i < 8; i++){
				windows.get(i).setPosition(x+5+(i*width/4)%width, y+5+(int)(i/4)*height/2, (width-40)/4, (height-20)/2);
			}
			lastAlt = true;
		}
		
		if(nbZones == 9){
			for(int i = 0; i < nbZones; i++){
				windows.get(i).setPosition(x+5+(i*width/3)%width, y+5+(int)(i/3)*height/3, (width-30)/3, (height-30)/3);
			}
			lastAlt = true;
		}
		
	}
	
	public void updateWindows(ArrayList<Window> windows){
		this.windows = windows;
	}
	
	public void updateImages(ArrayList<ImageModule> images){
		this.modules = images;
	}

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() > 96 && ke.getKeyCode() < 106){
			this.currentImage = new ImageMainView(modules.get(ke.getKeyCode()-97));
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		this.movesMouse = true;
	}
	
	public void addTuioCursor(TuioCursor tc) {
		if(tc.getCursorID() == 0){
			this.movesTUIO = true;
		}
	}
	
	public void removeTuioCursor(TuioCursor tc) {
		if(tc.getCursorID() == 0){
			this.movesTUIO = false;
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void addTuioBlob(TuioBlob t) {}
	public void addTuioObject(TuioObject t) {}
	public void refresh(TuioTime t) {}
	public void removeTuioBlob(TuioBlob t) {}
	public void removeTuioObject(TuioObject t) {}
	public void updateTuioBlob(TuioBlob t) {}
	public void updateTuioCursor(TuioCursor t) {}
	public void updateTuioObject(TuioObject t) {}
	
	public void mousePressed(MouseEvent e) {
		for(Window w : windows){
			w.isInside(e.getX(), e.getY());
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		this.movesMouse = false;
	}
	
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	
}
