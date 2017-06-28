package MainViewTest;

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
import Shared.ImageModule;
import Shared.TitleLayer;
import Shared.Window;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class MainViewTestPanel extends JPanel implements MouseListener, KeyListener, TuioListener {
		
	private static final long serialVersionUID = 1L;
	protected int width, height;
	protected int x, y;
	protected Dimension d;
	protected BackgroundLayer bg;
	protected TitleLayer tl;
	protected HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();
	protected TestLayer test;
	protected boolean menu;
	protected boolean connect;
	protected boolean alive;
	
	
	public MainViewTestPanel(Dimension d){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.x = 0;
		this.y = 0;
		this.d = d;
		this.bg = new BackgroundLayer(new Color(80, 80, 80), new Color(50, 50, 50), new Color(50, 50, 50), 120, d);
		Color mainColor = new Color(0, 241, 181);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly MainView Test", "0.17", options, colors, 0.5f, d);
		this.test = new TestLayer(4, d);
		this.menu = true;
		this.connect = false;
		this.alive = true;
		
	}
	
	public MainViewTestPanel(int x, int y, Dimension d){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.x = x;
		this.y = y;
		this.d = d;
		this.bg = new BackgroundLayer(new Color(80, 80, 80), new Color(50, 50, 50), new Color(50, 50, 50), 120, d);
		Color mainColor = new Color(0, 241, 181);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly MainView Test", "0.17", options, colors, 0.5f, d);
		this.test = new TestLayer(4, x, y, d);
		this.menu = true;
		this.connect = false;
		this.alive = true;
	}
	
	public void paintComponent(Graphics g){
		bg.paintComponent(g);
		if(menu){
			tl.paintComponent(g);
			if(tl.getMenu() == false){
				menu = false;
				tl.setMenu(true);
			}
			Font font = new Font("Calibri", Font.ITALIC, 80);
			String con;
			Color col;
			if(!connect){
				col = Color.RED;
				con = "Non connecté";
				CenterText.center((Graphics2D)g, con, font, 80, col, width-418, height-140, new Dimension(300, 60));
			}
			else{
				col = new Color(153, 204, 0);
				con = "Connecté";
				CenterText.center((Graphics2D)g, con, font, 80, col, width-347, height-140, new Dimension(300, 60));
			}
			
		}
		else{
			test.paintComponent((Graphics2D)g);
		}
		checkIfAlive();
	}
	
	public void checkIfAlive(){
		if(!this.alive){
			escape();
		}
	}
	
	public boolean getAlive(){
		return(this.alive);
	}
	
	public boolean getMenu(){
		return(this.menu);
	}
	
	public boolean moves(){
		return(test.moves());
	}
	
	public void updateWindows(ArrayList<Window> windows){
		test.updateWindows(windows);;
	}
	
	public ArrayList<Window> getWindows(){
		return(test.getWindows());
	}
	
	public void updateImages(ArrayList<ImageModule> images){
		test.updateImages(images);
	}
	
	public void mouseClicked(MouseEvent e) {
		this.tl.mouseClicked(e);
	}
	
	public void addTuioCursor(TuioCursor tc) {
		if(!menu){
			this.test.addTuioCursor(tc);
		}
	}
	
	public void removeTuioCursor(TuioCursor t) {
		if(!menu){
			this.test.removeTuioCursor(t);
		}
	}
	
	public void keyPressed(KeyEvent ke) {
		if(!menu){
			this.test.keyPressed(ke);
		}
		if(ke.getKeyCode() == ke.VK_ESCAPE){
			escape();
		}
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}

	//Fonction de fermeture du programme
	public void escape(){
		System.out.println("Fermeture du programme.");
		Frame[] frames = JFrame.getFrames();
		frames[0].dispatchEvent(new WindowEvent(frames[0], WindowEvent.WINDOW_CLOSING));
	}
	
	public void mousePressed(MouseEvent e) {
		test.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		test.mouseReleased(e);
	}
	
	public void setConnect(boolean connect){
		this.connect = connect;
	}
	
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
