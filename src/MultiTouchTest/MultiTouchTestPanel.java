package MultiTouchTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JPanel;

import Shared.BackgroundLayer;
import Shared.TitleLayer;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class MultiTouchTestPanel extends JPanel implements MouseListener, TuioListener {
		
	private static final long serialVersionUID = 1L;
	protected int x, y;
	protected Dimension d;
	protected BackgroundLayer bg;
	protected TitleLayer tl;
	protected HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();
	protected TestLayer test;
	protected boolean menu;
	
	public MultiTouchTestPanel(Dimension d){
		this.x = 0;
		this.y = 0;
		this.d = d;
		this.bg = new BackgroundLayer(new Color(255, 220, 220), new Color(255, 180, 180), new Color(255, 180, 180), 120, d);
		Color mainColor = new Color(255, 40, 40);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly MultiTouch Test", "0.1", options, colors, 0.5f, d);
		this.test = new TestLayer(d);
		this.menu = true;
	}
	
	public MultiTouchTestPanel(int x, int y, Dimension d){
		this.x = x;
		this.y = y;
		this.d = d;
		this.bg = new BackgroundLayer(new Color(255, 213, 213), new Color(255, 190, 190), new Color(255, 190, 190), 120, d);
		Color mainColor = new Color(255, 40, 40);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly Area Test", "0.1", options, colors, 0.5f, d);
		this.test = new TestLayer(x, y, d);
		this.menu = true;
	}
	
	public void paintComponent(Graphics g){
		bg.paintComponent(g);
		if(menu){
			tl.paintComponent(g);
			if(tl.getMenu() == false){
				menu = false;
				tl.setMenu(true);
			}
		}
		else{
			test.paintComponent(g);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		this.tl.mouseClicked(e);
	}
	public void keyPressed(KeyEvent ke) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	@Override
	public void addTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTuioCursor(TuioCursor tc) {
		if(!menu){
			System.out.println("Curseur "+tc.getCursorID()+" - X : "+tc.getX()+" Y : "+tc.getY());
		}
	}

	@Override
	public void addTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(TuioTime arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioCursor(TuioCursor arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioBlob(TuioBlob arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioCursor(TuioCursor arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

}
