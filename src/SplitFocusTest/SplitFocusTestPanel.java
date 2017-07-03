package SplitFocusTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Shared.BackgroundLayer;
import Shared.CenterText;
import Shared.TitleLayer;
import Shared.Window;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class SplitFocusTestPanel extends JPanel implements MouseListener, KeyListener, TuioListener {
		
	//VARIABLES
	private static final long serialVersionUID = 1L;
	
	//G�n�ral
	protected int width, height; //Taille de la fen�tre
	protected int x, y; //Point de d�part de l'affichage de la fen�tre
	protected Dimension d; //Dimension de la fen�tre
	
	//Couches
	protected BackgroundLayer bg; //Fond d'�cran
	protected TitleLayer tl; //Couche titre
	protected TestLayer test; //Couche test
	
	//Divers
	protected HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>(); //Liste des options (non utilis�)
	
	//Bool�ens
	protected boolean menu; //True si l'on est sur le menu
	protected boolean connect; //True si l'on est connect� au client
	protected boolean alive; //True si le programme n'a pas �t� ferm� sur aucun des deux �crans
	
	//CONSTRUCTEURS
	public SplitFocusTestPanel(Dimension d){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.x = 0;
		this.y = 0;
		this.d = d;
		setup();
	}
	
	public SplitFocusTestPanel(int x, int y, Dimension d){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.x = x;
		this.y = y;
		this.d = d;
		setup();
	}
	
	//Fonction commune aux deux constructeurs
	public void setup(){
		this.bg = new BackgroundLayer(new Color(200, 238, 255), new Color(130, 215, 215), new Color(130, 215, 215), 120, d);
		Color mainColor = new Color(0, 174, 255);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly SplitFocus Test", "0.25", options, colors, 0.5f, d);
		this.test = new TestLayer(2, d);
		this.connect = false;
		this.menu = true;
		this.alive = true;
	}
	
	//M�THODES - G�N�RAL
	
	public boolean checkIfMoves(){
		return(test.checkIfMoves());
	}
	
	//M�THODES - AFFICHAGE
	
	public void paintComponent(Graphics g){
		bg.paintComponent(g);
		
		//Si on est sur le menu
		if(menu){
			tl.paintComponent(g);
			Font font = new Font("Calibri", Font.ITALIC, 80);
			String con;
			Color col;
			//Affichage de la connexion au client
			if(!connect){
				col = Color.RED;
				con = "Non connect�";
				CenterText.center((Graphics2D)g, con, font, 80, col, width-418, height-140, new Dimension(300, 60));
			}
			else{
				col = new Color(153, 204, 0);
				con = "Connect�";
				CenterText.center((Graphics2D)g, con, font, 80, col, width-347, height-140, new Dimension(300, 60));
			}	
			//Si on clique sur le bouton, on passe au test
			if(tl.getMenu() == false){
				menu = false;
				tl.setMenu(true);
			}	
		}
		
		//Sinon
		else{
			test.paintComponent((Graphics2D)g);
		}
	}
	
	//M�THODES - GETTERS/SETTERS
	
	public boolean getMenu(){
		return(menu);
	}
	
	public void setControl(String control){
		test.setControl(control);
	}
	
	public void setConnect(boolean connect){
		this.connect = connect;
	}
	
	public ArrayList<Window> getWindows(){
		return(test.getWindows());
	}
	
	public void setWindows(ArrayList<Window> windows){
		test.setWindows(windows);
	}
	
	public boolean getAlive(){
		return(this.alive);
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	
	//M�THODES - ENTR�ES SOURIS/CLAVIER/TUIO
	
	//Souris press�e
	public void mouseClicked(MouseEvent e) {
		this.tl.mouseClicked(e);
	}
	
	//Tactile press�
	public void addTuioCursor(TuioCursor tc) {
		if(!menu){
			this.test.addTuioCursor(tc);
		}
	}
	
	//Tactile rel�ch�
	public void removeTuioCursor(TuioCursor t) {
		if(!menu){
			this.test.removeTuioCursor(t);
		}
	}
	
	//Touche press�e	
	public void keyPressed(KeyEvent ke) {
		if(!menu){
			this.test.keyPressed(ke);
		}
		if(ke.getKeyCode() == ke.VK_ESCAPE){
			escape();
		}
	}
	
	//Fonction de fermeture du programme
	public void escape(){
		this.alive = false; //On passe alive � false
		System.out.println("Fermeture du programme.");
		Frame[] frames = JFrame.getFrames();
		frames[0].dispatchEvent(new WindowEvent(frames[0], WindowEvent.WINDOW_CLOSING));
	}
	
	//Souris press�e
	public void mousePressed(MouseEvent e) {
		test.mousePressed(e);
	}
	
	//Souris rel�ch�e
	public void mouseReleased(MouseEvent e) {
		test.mouseReleased(e);
	}
	
	//Fonctions non-utilis�es forc�es par l'interfaces
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void addTuioBlob(TuioBlob t) {}
	public void addTuioObject(TuioObject t) {}
	public void refresh(TuioTime t) {}
	public void removeTuioBlob(TuioBlob t) {}
	public void removeTuioObject(TuioObject t) {}
	public void updateTuioBlob(TuioBlob t) {}
	public void updateTuioCursor(TuioCursor t) {}
	public void updateTuioObject(TuioObject t) {}

}
