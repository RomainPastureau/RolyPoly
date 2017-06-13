package ResolutionTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Button implements MouseListener {
	
	protected int x, y;
	protected Dimension d;
	protected ArrayList<Color> colors = new ArrayList<Color>();
	protected Color currentColor;
	protected boolean shadow;
	protected String titre;
	protected boolean value;
	
	public Button(int x, int y, Dimension d, Color buttonColor, String titre, boolean value){
		this.x = x;
		this.y = y;
		this.d = d;
		Color[] tempCol = {buttonColor.brighter(), buttonColor, buttonColor.darker()};
		this.colors.addAll(Arrays.asList(tempCol));
		this.shadow = true;
		this.titre = titre;
		this.value = value;
		this.currentColor = buttonColor;
	}
	
	public void paintComponent(Graphics g){
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		
		if(shadow){
			g.setColor(new Color(128, 128, 128));
			g.fillRect(x+5, y-5, (int)d.getWidth(), (int)d.getHeight());
			g.setColor(currentColor);
			g.fillRect(x, y, (int)d.getWidth(), (int)d.getHeight());
		}
		else{
			g.setColor(currentColor);
			g.fillRect(x+5, y-5, (int)d.getWidth(), (int)d.getHeight());
		}
		mouseHover(mouse);
		Graphics2D g2d = (Graphics2D)g;
		Font font = new Font("Calibri", Font.ITALIC, 80);
		CenterText.center(g2d, titre, font, 80, Color.BLACK, x, y, d);
	}
	
	public void mouseHover(Point mouse){
		if(x < mouse.getX() && mouse.getX() < x+d.getWidth() && y < mouse.getY() && mouse.getY() < y+d.getHeight()){
			this.currentColor = colors.get(2);
			shadow = false;
		}
		else{
			this.currentColor = colors.get(1);
			shadow = true;
		}
	}

	public void mouseClicked(MouseEvent e) {
		if(x < e.getX() && e.getX() < x+d.getWidth() && y < e.getY() && e.getY() < y+d.getHeight()){
			this.currentColor = colors.get(2);
			value = !value;
			this.shadow = false;
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getValue(){
		return(this.value);
	}
	
	public void setValue(boolean value){
		this.value = value;
	}
}