package MainViewTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
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

import Shared.ImageModule;
import Shared.Layer;
import Shared.Window;
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
	protected int currentImage;
	protected int alt; //Définit le numéro de l'arrangement pour un nombre de fenêtres données 
	protected int previous;
	protected boolean lastAlt, movesMouse, movesTUIO;
	protected LateralBar lb;
	protected String control;
	
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
		
		//Création de la liste de couleurs des fenêtres
		this.colors = new ArrayList<Color>();
		this.colors.addAll(Arrays.asList(tempColor));
		
		//Définition de l'arrangement de départ
		this.alt = 0;
		this.previous = nbZones;
		this.lastAlt = false;
		
		//Définition de la mobilité
		this.movesMouse = false;
		this.movesTUIO = false;
		
		//Définition des fenêtres
		this.windows = new ArrayList<Window>();
		for(int i = 0; i < 9; i++){
			this.windows.add(new Window(i, 0, 0, 0, 0, colors.get(i), 0, 1.f));
		}
		
		//Ouverture des fichiers images
		File f = new File(System.getProperty("user.dir")+"/images2");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		
		//Définition des modules images
		this.modules = new ArrayList<ImageModule>();
		System.out.print("Lecture des fichiers image...");
		for(int i = 0; i < 1; i++){
			try{
				BufferedImage bim = ImageIO.read(files.get(0));
				ImageModule mod = new ImageModule(bim);
				this.modules.add(mod);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		System.out.println(" Terminé.");
		
		this.movesMouse = false;
		this.movesTUIO = false;
		this.control = "SplitView";
		
		//Mise en place de l'arrangement initial
		if(nbZones == 4){
			if(alt == 0){
				for(int i = 0; i < 4; i++){
					windows.get(i).setPosition(x+5+(i*width/2)%width, y+5+(int)(i/2)*height/2, (width-20)/2, (height-20)/2);
				}
			}
		}
		
		//Définit l'image à l'ouverture
		this.currentImage = 0;
		
		//Définition de la barre latérale
		this.lb = new LateralBar("Vertical", "Droite", windows, modules, currentImage);
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		//Affichage de l'image en cours
		ImageModule img = modules.get(currentImage);
		img.paintComponent(g2d);
		
		//Calcul du ratio de l'image en cours
		float ratio = img.getRatio();
		
		//Récupération des informations du pointeur
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		
		//Affichage des fenêtres courantes
		for(Window win : windows){
			if(win.getActive() && control == "MainView"){
				currentImage = win.getModuleID();
				move((int)mouse.getX(), (int)mouse.getY(), win, img);
			}
			if(win.getModuleID() == currentImage && win.getWidth() != 0){
				win.update(ratio, img.getStartX(), img.getStartY());
				win.paintComponentMain(g2d);
			}
		}
		
		//Affichage de la barre latérale
		lb.update(windows, currentImage);
		lb.paintComponent(g2d);
	}
	
	public boolean moves(){
		return(this.movesMouse || this.movesTUIO);
	}
	
	public ArrayList<Window> getWindows(){
		return(this.windows);
	}
	
	public void setControl(String control){
		this.control = control;
	}
	
	public String getControl(){
		return(this.control);
	}
	
	public void move(int a, int b, Window w, ImageModule img){
		
		if(w.getActive()){
			w.setMainX(w.getLastMainX()+(a-w.getContactX()), img);
			w.setMainY(w.getLastMainY()+(b-w.getContactY()), img);
		}
		if(w.getMainX() > img.getStartX()+img.getResizedWidth()-w.getMainWidth()){
			w.setMainX(img.getStartX()+img.getResizedWidth()-w.getMainWidth(), img);
		}
		if(w.getMainX() < img.getStartX()){
			w.setMainX(img.getStartX(), img);
		}
		if(w.getMainY() > img.getStartY()+img.getResizedHeight()-w.getMainHeight()){
			w.setMainY(img.getStartY()+img.getResizedHeight()-w.getMainHeight(), img);
		}
		if(w.getMainY() < img.getStartY()){
			w.setMainY(img.getStartY(), img);
		}
	}
	
	public void updateWindows(ArrayList<Window> windows){
		this.windows = windows;
		lb.update(windows, currentImage);
	}
	
	public void updateImages(ArrayList<ImageModule> images){
		this.modules = images;
	}

	public void keyPressed(KeyEvent ke) {
		
		if(ke.getKeyCode() > 96 && ke.getKeyCode() < 106){
			this.currentImage = ke.getKeyCode()-97;
		}
		else if(ke.getKeyCode() == 37){
			lb.setPosition("Gauche");
			lb.setOrientation("Vertical");
		}
		else if(ke.getKeyCode() == 38){
			lb.setPosition("Haut");
			lb.setOrientation("Horizontal");
		}
		else if(ke.getKeyCode() == 40){
			lb.setPosition("Bas");
			lb.setOrientation("Horizontal");
		}
		else if(ke.getKeyCode() == 39){
			lb.setPosition("Droite");
			lb.setOrientation("Vertical");
		}
		else if(ke.getKeyCode() == 32){
			lb.setVisible();
		}
		lb.update(windows, currentImage);
	}
	
	public void mouseClicked(MouseEvent e) {
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
		this.movesMouse = true;
		for(Window w : windows){
			w.isInsideMain(e.getX(), e.getY(), currentImage);
		}	
		currentImage = lb.selectImage(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		this.movesMouse = false;
		for(Window w:windows){
			w.setInactive();
			w.updateCornerMain();
		}
	}
	
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	
}
