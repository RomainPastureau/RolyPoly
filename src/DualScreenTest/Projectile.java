package DualScreenTest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Projectile implements Serializable {
	
	private static final long serialVersionUID = 7488924923133410267L;
	protected int x, y;
	protected int startX, startY;
	protected int width, height;
	protected long timeCreated;
	protected int speed;
	protected boolean exists;
	
	public Projectile(int x, int y, int width, int height, int carWidth, int speed){
		this.x = x + (carWidth/2) - (width/2);
		this.y = y;
		this.width = width;
		this.height = height;
		this.startX = x + (width/2);
		this.startY = y;
		this.speed = speed;
		this.timeCreated = System.currentTimeMillis();
		this.exists = true;
	}
	
	public void paintComponent(Graphics2D g){
		if(this.exists){
			double timeNow = (System.currentTimeMillis() - this.timeCreated)/(1000.0);
			this.y = this.startY - (int)(timeNow*this.speed);
			if(this.y < 0){
				this.exists = false;
			}
			if((int)timeNow%2 == 0){
				g.setColor(new Color((int)(255*(timeNow%1)), 0, 255-(int)(255*(timeNow%1))));
			}
			else{
				g.setColor(new Color(255-(int)(255*(timeNow%1)), 0, (int)(255*(timeNow%1))));
			}
			g.fillRect(x, y, width, height);
		}
	}
	
	public void setExists(boolean exists){
		this.exists = exists;
	}

}
