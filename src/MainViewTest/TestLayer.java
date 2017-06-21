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
	protected ImageMainView currentImage;
	protected int alt; //Définit le numéro de l'arrangement pour un nombre de fenêtres données 
	protected int previous;
	protected boolean lastAlt;
	
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
		
		//Assigne une couleur à chaucne des fenêtres
		for(int i = 0; i < 9; i++){
			this.windows.add(new Window(i, 0, 0, 0, 0, colors.get(i)));
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
		
		//Définit l'image à l'ouverture
		this.currentImage = new ImageMainView(modules.get(0));
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		//Affichage de l'image en cours
		currentImage.paintComponent(g2d);
	}

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() > 96 && ke.getKeyCode() < 106){
			this.currentImage = new ImageMainView(modules.get(ke.getKeyCode()-97));
		}
	}
	
	public void mouseClicked(MouseEvent e) {

	}
	
	public void addTuioCursor(TuioCursor tc) {
		
	}
	
	public void removeTuioCursor(TuioCursor t) {

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
	}
	
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	
}
