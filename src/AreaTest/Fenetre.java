package AreaTest;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import Shared.PressKey;

public class Fenetre extends JFrame implements MouseListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	protected int width, height;
	protected PressKey pk;
	protected AreaTestPanel mzt;
	
	public Fenetre(){
		//Titre de fenêtre
		this.setTitle("RolyPoly Area Test 0.6");
		
		//Taille de la fenêtre
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		this.setSize(width, height);

		//Plein écran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//Action à la fermeture
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		mzt = new AreaTestPanel(this.screenSize);
		this.setContentPane(mzt);
		
		//Entrées clavier
		pk = new PressKey();
		this.addKeyListener(pk);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}	
	
	public void go(){
		while(true){
			mzt.repaint();
		}
	}

	public void mouseClicked(MouseEvent e) {
		mzt.mouseClicked(e);		
	}
	
	public void keyPressed(KeyEvent ke) {
		mzt.keyPressed(ke);
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
