package AreaTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class Window {

	protected int x, y, id, width, height;
	protected Dimension d;
	protected boolean active;
	protected Color color;
	protected long time;
	
	public Window(int id, int x, int y, int width, int height, Color color){
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.active = false;
		this.time = 0;
	}
	
	public void setPosition(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean isInside(int x, int y){
		if(this.x < x && x < d.getWidth() && this.y < y && y < d.getHeight()){
			this.active = true;
			this.time = System.currentTimeMillis();;
			return(true);
		}
		this.active = false;
		return(false);
	}
	
	public void paintComponent(Graphics2D g2d){
		g2d.setColor(color);
		g2d.drawRect(x, y, width, height);
		if(active){
			long now = System.currentTimeMillis()-time;
			if(now > 255){
				now = 255;
			}
			g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255 - (now*128)));
			g2d.fillRect(x, y, width, height);
		}
	}
	
}
