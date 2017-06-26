package SplitFocusTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	
	public SplitFocusTestPanel(Dimension d){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.x = 0;
		this.y = 0;
		this.d = d;
		this.bg = new BackgroundLayer(new Color(200, 238, 255), new Color(130, 215, 215), new Color(130, 215, 215), 120, d);
		Color mainColor = new Color(0, 174, 255);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly SplitFocus Test", "0.3", options, colors, 0.5f, d);
		this.test = new TestLayer(2, d);
		this.connect = false;
		this.menu = true;
	}
	
	public SplitFocusTestPanel(int x, int y, Dimension d){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.x = x;
		this.y = y;
		this.d = d;
		this.bg = new BackgroundLayer(new Color(200, 238, 255), new Color(130, 215, 215), new Color(130, 215, 215), 120, d);
		Color mainColor = new Color(0, 174, 255);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly MultiTouch Test", "0.3", options, colors, 0.5f, d);
		this.test = new TestLayer(2, x, y, d);
		this.connect = false;
		this.menu = true;
	}
	
	public void paintComponent(Graphics g){
		bg.paintComponent(g);
		
		if(menu){
			tl.paintComponent(g);
			Font font = new Font("Calibri", Font.ITALIC, 80);
			String con;
			Color col;
			if(!connect){
				col = Color.RED;
				con = "Non connect�";
			}
			else{
				col = new Color(153, 204, 0);
				con = "Connect�";
			}
			CenterText.center((Graphics2D)g, con, font, 80, col, width-300, height-140, new Dimension(300, 60));

			if(tl.getMenu() == false){
				menu = false;
				tl.setMenu(true);
			}	
		}
		
		else{
			test.paintComponent((Graphics2D)g);
		}
	}
	
	public boolean getMenu(){
		return(menu);
	}
	
	public void setConnect(boolean connect){
		this.connect = connect;
	}
	
	public boolean moves(){
		return(test.moves());
	}
	
	public ArrayList<Window> getWindows(){
		return(test.getWindows());
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
	}
	
	public void mousePressed(MouseEvent e) {
		test.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		test.mouseReleased(e);
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