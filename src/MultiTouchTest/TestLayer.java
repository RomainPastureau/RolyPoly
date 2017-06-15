package MultiTouchTest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import Shared.Layer;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class TestLayer extends Layer implements KeyListener, MouseListener, TuioListener {
	
	protected HashMap<Integer, TouchPoint> cursors;
	
	public TestLayer(Dimension d){
		super(d);
		cursors = new HashMap<Integer, TouchPoint>();
	}
	
	public TestLayer(int x, int y, Dimension d){
		super(x, y, d);
		cursors = new HashMap<Integer, TouchPoint>();
	}
	
	public void paintComponent(Graphics g){
		for(int i:cursors.keySet()){
			cursors.get(i).paintComponent((Graphics2D)g);
		}
	}
	
	public void addTuioCursor(TuioCursor t) {
		System.out.println("Curseur "+t.getCursorID()+" - X : "+t.getX()+" Y : "+t.getY());
		cursors.put(t.getCursorID(), new TouchPoint(t));		
	}
	
	public void removeTuioCursor(TuioCursor t) {
		cursors.remove(t.getCursorID());
	}

	public void keyPressed(KeyEvent ke) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	public void addTuioBlob(TuioBlob t) {}
	public void addTuioObject(TuioObject t) {}
	public void refresh(TuioTime t) {}
	public void removeTuioBlob(TuioBlob t) {}
	public void removeTuioObject(TuioObject t) {}
	public void updateTuioBlob(TuioBlob t) {}
	public void updateTuioCursor(TuioCursor t) {}
	public void updateTuioObject(TuioObject t) {}
	
}
