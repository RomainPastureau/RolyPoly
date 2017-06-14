package AreaTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class Window {

	protected int x, y, id, width, height;
	protected Dimension d;
	protected boolean active;
	protected Color color;
	protected long time, now;
	
	public Window(int id, int x, int y, int width, int height, Color color){
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.active = false;
		this.time = 0;
		this.now = 0;
	}
	
	public void setPosition(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean isInside(int x, int y){
		if(this.x < x && x < this.x+width && this.y < y && y < this.y+height){
			this.active = true;
			this.time = System.currentTimeMillis();;
			this.now = 0;
			return(true);
		}
		this.active = false;
		return(false);
	}
	
	public void paintComponent(Graphics2D g2d){
		g2d.setColor(color);
		g2d.drawRect(x, y, width, height);
		if(active){
			if(now < 2000){
				now = System.currentTimeMillis()-time;
			}
			if(now >= 2000){
				now = 2000;
			}
			g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(128 - ((now/1000.)*64))));
			g2d.fillRect(x, y, width, height);
		}
	}
	
}
