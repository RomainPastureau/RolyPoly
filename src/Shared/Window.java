package Shared;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Window implements Serializable {

	private static final long serialVersionUID = 3764602074015741068L;
	protected int x, y, id, width, height;
	protected int mainX, mainY, mainWidth, mainHeight;
	protected int contactX, contactY;
	protected Dimension d;
	protected boolean active, current;
	protected Color color;
	protected long time, now;
	protected boolean canChangeColor;
	protected int moduleID;
	protected int startX, startY, lastStartX, lastStartY;
	protected float ratio;
	
	public Window(int id, int x, int y, int width, int height, Color color, int moduleID, float ratio){
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ratio = ratio;
		this.mainWidth = (int)(ratio*width);
		this.mainHeight = (int)(ratio*height);
		this.color = color;
		this.active = false;
		this.current = false;
		this.time = 0;
		this.now = 2000;
		this.canChangeColor = false;
		this.moduleID = moduleID;
		this.startX = 0;
		this.startY = 0;
		this.mainX = 0;
		this.mainY = 0;
		this.lastStartX = 0;
		this.lastStartY = 0;
	}
	
	public void setPosition(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setMainPosition(int x, int y, int width, int height){
		this.startX = x;
		this.startY = y;
		this.width = width;
		this.height = height;
	}
	
	public void setModuleID(int moduleID){
		this.moduleID = moduleID;
	}
	
	public int getModuleID(){
		return(this.moduleID);
	}
	
	public void updateCorner(){
		this.lastStartX = this.startX;
		this.lastStartY = this.startY;
	}
	
	public boolean isInside(int x, int y){
		if(this.x < x && x < this.x+width && this.y < y && y < this.y+height){
			if(!this.current){
				this.canChangeColor = true;
				this.time = System.currentTimeMillis();
				this.now = 0;
			}
			else if(this.now >= 2000){
				this.canChangeColor = false;
			}
			this.current = true;
			this.active = true;
			this.contactX = x;
			this.contactY = y;
			
			return(true);
		}
		this.current = false;
		this.active = false;
		return(false);
	}
	
	public boolean isInsideMain(int x, int y){
		if(this.startX < x && x < this.startX+mainWidth && this.startY < y && y < this.startY+mainHeight){
			if(!this.current){
				this.canChangeColor = true;
				this.time = System.currentTimeMillis();
				this.now = 0;
			}
			else if(this.now >= 2000){
				this.canChangeColor = false;
			}
			this.current = true;
			this.active = true;
			this.contactX = x;
			this.contactY = y;
			
			return(true);
		}
		this.current = false;
		this.active = false;
		return(false);
	}
	
	public void paintComponent(Graphics2D g2d){
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawRect(x, y, width, height);
		if(canChangeColor){
			if(now < 2000){
				now = System.currentTimeMillis()-time;
			}
			if(now >= 2000){
				now = 2000;
				canChangeColor = false;
			}
		}
		g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(128 - ((now/1000.)*64))));
		g2d.fillRect(x, y, width, height);
	}
	
	public void paintComponentMain(Graphics2D g2d){
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawRect(mainX, mainY, mainWidth, mainHeight);
		if(canChangeColor){
			if(now < 2000){
				now = System.currentTimeMillis()-time;
			}
			if(now >= 2000){
				now = 2000;
				canChangeColor = false;
			}
		}
		g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(128 - ((now/1000.)*64))));
		g2d.fillRect(mainX, mainY, mainWidth, mainHeight);  
	}
	
	public Color getColor(){
		return(this.color);
	}
	
	public void setInactive(){
		this.active = false;
	}
	
	public int getID(){
		return(this.id);
	}
	
	public int getX(){
		return(this.x);
	}
	
	public int getY(){
		return(this.y);
	}
	
	public int getWidth(){
		return(this.width);
	}
	
	public int getHeight(){
		return(this.height);
	}
	
	public int getContactX(){
		return(this.contactX);
	}
	
	public int getContactY(){
		return(this.contactY);
	}
	
	public boolean getActive(){
		return(this.active);
	}
	
	public int getStartX(){
		return(this.startX);
	}
	
	public int getStartY(){
		return(this.startY);
	}
	
	public int getLastStartX(){
		return(this.lastStartX);
	}
	
	public int getLastStartY(){
		return(this.lastStartY);
	}
	
	public void setStartX(int startX){
		this.startX = startX;
	}
	
	public void setStartY(int startY){
		this.startY = startY;
	}
	
	public void update(float ratio, int imgStartX, int imgStartY){
		this.ratio = ratio;
		this.mainWidth = (int)(width*ratio);
		this.mainHeight = (int)(height*ratio);
		this.mainX = (int)(startX*ratio)+imgStartX; 
		this.mainY = (int)(startY*ratio)+imgStartY; 
	}
	
}
