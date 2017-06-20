package DualScreenTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Car {
	
	protected int x, y;
	protected int width, height;
	protected Color color;
	protected ArrayList<Projectile> projectiles;
	protected String type;
	protected Dimension d;
	
	public Car(int x, int y, int width, int height, Color color, String type, Dimension d){
		this.x = x; 
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.projectiles = new ArrayList<Projectile>();
		this.type = type;
		this.d = d;
	}
	
	public void paintComponent(Graphics2D g){
		g.setPaint(color);
		if(type == "Serveur"){
			int posX[] = {x+width/2, x, x+width};
			int posY[] = {y, y+height, y+height};
			g.fillPolygon(posX, posY, 3);
		}
		else{
			g.fillRect(x, (int)d.getHeight()/2-(height/2), width, height);
		}
		int size = projectiles.size();
		for(int i = 0; i < size; i++){
			if(!projectiles.get(i).exists){
				projectiles.remove(i);
				break;
			}
		}
		if(type == "Serveur"){
			for(Projectile p:projectiles){
				p.paintComponent(g);
			}
		}
	}
	
	public void move(int x){
		this.x = x;
	}
	
	public void shoot(){
		Projectile p = new Projectile(x, y, 4, 10, width, 150);
		projectiles.add(p);
	}
	
	public Coordinates getCar(){
		return(new Coordinates(x, y));
	}
	
	public void setCoordinates(Coordinates c){
		this.x = c.getX();
		this.y = c.getY();
	}
}
