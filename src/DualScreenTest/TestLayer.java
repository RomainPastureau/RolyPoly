package DualScreenTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Shared.Layer;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class TestLayer extends Layer implements KeyListener, MouseListener, TuioListener {
	
	protected Car car;
	protected TuioCursor t1;
	
	public TestLayer(Dimension d){
		super(d);
		this.car = new Car(width/2-50, height-150, 100, 100, Color.RED);
	}
	
	public void moveCar(){
		if(t1 != null){
			car.move((int)((t1.getX()*this.width)-car.width/2));
		}
	}
	
	public Car getCar(){
		return(car);
	}
	
	public void paintComponent(Graphics2D g){
		this.car.paintComponent(g);
		moveCar();
	}
	
	public void addTuioCursor(TuioCursor tc) {
		if(tc.getCursorID() == 0){
			t1 = tc;
		}
		else if(tc.getCursorID() == 1){
			car.shoot();
		}
	}
	
	public void removeTuioCursor(TuioCursor tc) {
		if(tc.getCursorID() == 0){
			t1 = null;
		}
	}

	public void keyPressed(KeyEvent ke) {
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
