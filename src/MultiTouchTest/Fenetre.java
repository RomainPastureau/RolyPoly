package MultiTouchTest;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import Shared.PressKey;
import TUIO.TuioBlob;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class Fenetre extends JFrame implements MouseListener, KeyListener, TuioListener {
	
	private static final long serialVersionUID = 1L;
	protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	protected int width, height;
	protected PressKey pk;
	protected MultiTouchTestPanel mtt;
	protected TuioClient client;
	
	public Fenetre(){
		
		//Titre de fenêtre
		this.setTitle("RolyPoly MultiTouch Test 1.8");
		
		//Taille de la fenêtre
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		this.setSize(width, height);

		//Plein écran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//Action à la fermeture
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mtt = new MultiTouchTestPanel(this.screenSize);
		this.setContentPane(mtt);
		
		//Entrées clavier
		pk = new PressKey();
		this.addKeyListener(pk);
		this.addKeyListener(this);
		
		//Entrées souris
		this.addMouseListener(this);
		
		//Entrées touch
		client = new TuioClient();
		client.addTuioListener(this);
		client.connect();
	}	
	
	public void go(){
		while(true){
			mtt.repaint();
		}
	}
	
	public void addTuioCursor(TuioCursor tc) {
		mtt.addTuioCursor(tc);
	}
	
	public void removeTuioCursor(TuioCursor tc) {
		mtt.removeTuioCursor(tc);
	}

	public void mouseClicked(MouseEvent e) {
		mtt.mouseClicked(e);		
	}
	
	public void keyPressed(KeyEvent ke) {
		mtt.keyPressed(ke);
	}
	
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
