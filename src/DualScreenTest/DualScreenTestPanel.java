package DualScreenTest;

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
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class DualScreenTestPanel extends JPanel implements MouseListener, KeyListener, TuioListener {
	
	private static final long serialVersionUID = 1L;
	protected int width, height;
	protected String type;
	protected boolean menu;
	protected BackgroundLayer bg;
	protected TitleLayer tl;
	protected TestLayer test;
	protected HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();
	protected boolean connect;

	public DualScreenTestPanel(Dimension d, String type){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.bg = new BackgroundLayer(new Color(247, 235, 235), new Color(229, 187, 255), new Color(229, 187, 255), 120, d);
		this.menu = true;
		if(type.equals("Client")){
			this.menu = false;
		}
		Color mainColor = new Color(156, 0, 255);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly DualScreen Test", "0.20", options, colors, 0.5f, d);
		this.type = type;
		this.test = new TestLayer(d, type);
		this.connect = false;
	}
	
	public Coordinates getCar(){
		return(test.getCar());
	}

	public void paintComponent(Graphics g){
		bg.paintComponent(g);
		if(menu){
			tl.paintComponent(g);
			Font font = new Font("Calibri", Font.ITALIC, 80);
			Color col;
			if(!connect){
				col = Color.RED;
			}
			else{
				col = new Color(153, 204, 0);
			}
			CenterText.center((Graphics2D)g, type, font, 80, col, width-300, height-140, new Dimension(300, 60));
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
		return(this.menu);
	}
	
	public void setConnect(boolean connect){
		this.connect = connect;
	}
	
	public void updateCoordinates(Coordinates c){
		test.updateCoordinates(c);
	}
	
	public void mouseClicked(MouseEvent e) {
		this.tl.mouseClicked(e);
	}
	public void addTuioCursor(TuioCursor tc) {
		test.addTuioCursor(tc);
	}
	public void removeTuioCursor(TuioCursor tc) {
		test.removeTuioCursor(tc);
	}
	public void keyPressed(KeyEvent ke) {}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
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
