package ResolutionTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CircleZone implements MouseListener {
	
	protected int x, y, tailleZone;
	protected boolean clicked;
	
	public CircleZone(int x, int y, int tailleZone){
		this.x = x;
		this.y = y;
		this.tailleZone = tailleZone;
		this.clicked = false;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		if(!clicked){
			g2d.setColor(new Color(196, 42, 42));
		}
		else{
			g2d.setColor(new Color(153, 204, 0));
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillOval(x, y, tailleZone, tailleZone);
	}

	public void mouseClicked(MouseEvent e) {
		//Carré
		if(x < e.getX() && e.getX() < x+tailleZone && y < e.getY() && e.getY() < y+tailleZone){
			this.clicked = true;
		}
		//Cercle
//		if(Math.sqrt((Math.pow(e.getX(),2)-x-(tailleZone/2))+(Math.pow(e.getY(),2)-y-(tailleZone/2))) <= tailleZone/2){
//			this.clicked = true;
//		}
	}
	
	public boolean getClicked(){
		return(this.clicked);
	}
	
	public void setClicked(boolean clicked){
		this.clicked = clicked;
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
