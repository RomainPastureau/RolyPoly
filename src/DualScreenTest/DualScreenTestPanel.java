package DualScreenTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class DualScreenTestPanel extends JPanel implements MouseListener, KeyListener, TuioListener {
	
	private static final long serialVersionUID = 1L;
	protected int width, height;
	protected Car car;
	protected TuioCursor t1, t2;
	protected String type;

	public DualScreenTestPanel(Dimension d, String type){
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
		this.car = new Car(width/2-50, height-150, 100, 100, Color.RED);
		this.type = type;
	}
	
	public void moveCar(){
		car.move((int)(t1.getX()+car.width/2));
	}
	
	public Car getCar(){
		return(car);
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void addTuioCursor(TuioCursor tc) {
		if(tc.getCursorID() == 0){
			t1 = tc;
		}
		else if(tc.getCursorID() == 1){
			car.shoot();
		}
	}
	public void removeTuioCursor(TuioCursor t) {}
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
