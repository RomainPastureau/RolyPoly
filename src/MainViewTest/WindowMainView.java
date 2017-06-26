package MainViewTest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class WindowMainView implements Serializable {
	
	private static final long serialVersionUID = 9179235663943019364L;
	protected int id, x, y;
	protected int contactX, contactY;
	protected int width, height;
	protected float ratio;
	protected Color color;
	protected long time, now;
	protected boolean active, current, canChangeColor;
	
	public WindowMainView(int id, int x, int y, int width, int height, float ratio, Color color){
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ratio = ratio;
		this.color = color;
		this.active = false;
		this.current = false;
		this.time = 0;
		this.now = 2000;
		this.canChangeColor = false;
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
	
	public void moveWindow(int x, int y){
		if(this.active){
			this.x = x;
			this.y = y;
		}
	}
	
	public void updatePosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void updateSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void updateRatio(float ratio){
		this.ratio = ratio;
	}

}
