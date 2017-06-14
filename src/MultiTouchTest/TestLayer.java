package MultiTouchTest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Shared.Layer;

public class TestLayer extends Layer implements KeyListener, MouseListener {
	
	public TestLayer(Dimension d){
		super(d);
	}
	
	public TestLayer(int x, int y, Dimension d){
		super(x, y, d);
	}
	
	public void paintComponent(Graphics g){
	}

	public void keyPressed(KeyEvent ke) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	
}
