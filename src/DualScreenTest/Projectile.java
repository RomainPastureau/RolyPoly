package DualScreenTest;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile {
	
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
		this.timeCreated = System.currentTimeMillis();
		this.exists = true;
	}
	
	public void paintComponent(Graphics2D g){
		if(this.exists){
			long timeNow = (System.currentTimeMillis() - this.timeCreated)/1000;
			this.y = this.startY - (int)(timeNow*this.speed);
			if(this.y < 0){
				this.exists = false;
			}
			if((int)(timeNow/1000)%2 == 0){
				g.setColor(new Color(255, 255*((timeNow/1000)%1000), 255*((timeNow/1000)%1000)));
			}
			else{
				g.setColor(new Color(255, 255-255*((timeNow/1000)%1000), 255-255*((timeNow/1000)%1000)));
			}
			g.fillRect(x, y, width, height);
		}
	}

}
