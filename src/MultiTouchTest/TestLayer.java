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
	protected boolean stay;
	
	public TestLayer(Dimension d){
		super(d);
		cursors = new HashMap<Integer, TouchPoint>();
		this.stay = true;
	}
	
	public TestLayer(int x, int y, Dimension d){
		super(x, y, d);
		cursors = new HashMap<Integer, TouchPoint>();
		this.stay = true;
	}
	
	public void paintComponent(Graphics g){
		for(int i = 0; i < cursors.keySet().size(); i++){
			try{
				if(this.stay || cursors.get(i).isVisible()){
					cursors.get(i).paintComponent((Graphics2D)g);
				}
			} catch(NullPointerException e){
				break;
			}
		}
	}
	
	public void addTuioCursor(TuioCursor t) {
		cursors.put(t.getCursorID(), new TouchPoint(t));
	}
	
	public void removeTuioCursor(TuioCursor t) {
		cursors.get(t.getCursorID()).setVisible(false);
		if(this.stay == false){
			cursors.remove(t.getCursorID());
		}
	}

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == ke.VK_ENTER){
			this.stay = !this.stay;
		}
	}
	
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
