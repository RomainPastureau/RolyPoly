package DualScreenTest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import Shared.Layer;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class TestLayer extends Layer implements KeyListener, MouseListener, TuioListener {
	
	protected Car car;
	protected TuioCursor t1;
	protected int yLine;
	protected Color lineColor;
	protected String type;
	
	
	
	public TestLayer(Dimension d, String type){
		super(d);
		this.car = new Car(width/2-50, height-150, 100, 100, new Color(156, 0, 255), type, d);
		this.yLine = height-50;
		this.lineColor = (new Color(156, 0, 255)).darker();
		this.type = type;
	}
	
	public void moveCar(){
		if(t1 != null){
			car.move((int)((t1.getX()*this.width)-car.width/2));
		}
	}
	
	public Coordinates getCar(){
		return(car.getCar());
	}
	
	public void updateCoordinates(Coordinates c){
		car.setCoordinates(c);
	}
	
	public void paintComponent(Graphics2D g){
		if(type == "Serveur"){
			g.setStroke(new BasicStroke(6));
			g.draw(new Line2D.Float(0, yLine, width, yLine));
		}
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
