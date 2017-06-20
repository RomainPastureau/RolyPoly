package DualScreenTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Car {
	
	protected Coordinates c;
	protected int width, height;
	protected Color color;
	protected ArrayList<Projectile> projectiles;
	protected String type;
	protected Dimension d;
	
	public Car(int x, int y, int width, int height, Color color, String type, Dimension d){
		this.c = new Coordinates(x, y);
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
			int posX[] = {c.getX()+width/2, c.getX(), c.getX()+width};
			int posY[] = {c.getY(), c.getY()+height, c.getY()+height};
			g.fillPolygon(posX, posY, 3);
		}
		else{
			g.fillRect(c.getX(), (int)d.getHeight()/2-(height/2), width, height);
		}
		int size = projectiles.size();
		for(int i = 0; i < size; i++){
			if(!projectiles.get(i).exists){
				projectiles.remove(i);
				break;
			}
		}
		if(type == "Serveur"){
			for(int i = 0; i < size; i++){
				try{
					projectiles.get(i).paintComponent(g);
				} catch(IndexOutOfBoundsException e){
					break;
				}
			}
		}
	}
	
	public void move(int x){
		c.setX(x);
	}
	
	public void shoot(){
		Projectile p = new Projectile(c.getX(), c.getY(), 4, 10, width, 150);
		projectiles.add(p);
	}
	
	public Coordinates getCar(){
		return(c);
	}
	
	public void setCoordinates(Coordinates c){
		this.c = c;
	}
}
