package DualScreenTest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Car {
	
	protected int x, y;
	protected int width, height;
	protected Color color;
	protected ArrayList<Projectile> projectiles;
	
	public Car(int x, int y, int width, int height, Color color){
		this.x = x; 
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.projectiles = new ArrayList<Projectile>();
	}
	
	public void paintComponent(Graphics2D g){
		g.setPaint(color);
		g.fillRect(x, y, width, height);
	}
	
	public void move(int x){
		this.x = x;
	}
	
	public void shoot(){
		Projectile p = new Projectile(x, y, 4, 10, width, 10);
		projectiles.add(p);
	}
	
	public String toString(){
		return("Position en X : "+x+", Position en Y : "+y);
	}

}
