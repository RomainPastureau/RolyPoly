package DualScreenTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Shared.CenterText;

public class Car {
	
	protected Coordinates c;
	protected int width, height;
	protected Color color;
	protected ArrayList<Projectile> projectiles;
	protected ArrayList<Meteor> meteors;
	protected String type;
	protected Dimension d;
	protected long startTime, now, timeUntilNext;
	protected int randomNum;
	protected boolean over;
	
	public Car(int x, int y, int width, int height, Color color, String type, Dimension d){
		this.c = new Coordinates(x, y);
		this.width = width;
		this.height = height;
		this.color = color;
		this.projectiles = new ArrayList<Projectile>();
		this.randomNum = ThreadLocalRandom.current().nextInt(0, 1);
		this.meteors = new ArrayList<Meteor>();
		this.startTime = System.currentTimeMillis();
		this.timeUntilNext = 5000;
		this.type = type;
		this.d = d;
		this.over = false;
	}
	
	public void paintComponent(Graphics2D g){
		if(!over){
			g.setPaint(color);
			now = System.currentTimeMillis()-startTime;
			if(type == "Serveur"){
				int posX[] = {c.getX()+width/2, c.getX(), c.getX()+width};
				int posY[] = {c.getY(), c.getY()+height, c.getY()+height};
				g.fillPolygon(posX, posY, 3);
			}
			else{
				g.fillRect(c.getX(), (int)d.getHeight()/2-(height/2), width, height);
			}
			int size = meteors.size();
			for(int i = 0; i < size; i++){
				if(!meteors.get(i).exists){
					meteors.remove(i);
					break;
				}
			}
			for(int i = 0; i < size; i++){
				try{
					meteors.get(i).paintComponent(g);
				} catch(IndexOutOfBoundsException e){
					break;
				}
			}
			size = projectiles.size();
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
			if(now > timeUntilNext){
				int meteorSize = ThreadLocalRandom.current().nextInt(5, 100);
				int meteorSpeed = ThreadLocalRandom.current().nextInt(1, 50);
				int meteorX = ThreadLocalRandom.current().nextInt(0, (int)d.getWidth()-meteorSize);
				Meteor meteor = new Meteor(meteorX, 0, meteorSize, meteorSpeed, type, d);
				meteors.add(meteor);
				timeUntilNext = (long)(timeUntilNext*0.95);
			}
			checkIfTouches();
			checkIfOver();
		}
		else{
			g.setColor(new Color(255, 255, 255, 128));
			g.fillRect(0, 0, (int)d.getWidth(), (int)d.getHeight());
			Font font = new Font("Calibri", Font.ITALIC, 80);
			CenterText.center((Graphics2D)g, "Game Over", font, 200, Color.RED, 0, 0, d);
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
	
	public void checkIfOver(){
		for(Meteor m:meteors){
			if(m.y >= d.getHeight()-height-50){
				over = true;
			}
		}
	}
	
	public void checkIfTouches(){
		int sizeProj = projectiles.size();
		int sizeMeteors = meteors.size();
		boolean check = false;
		
		for(int i = 0; i < sizeMeteors; i++){
			for(int j = 0; i < sizeProj; j++){
				check = meteors.get(i).checkIfTouches(projectiles.get(j));
				if(check){
					meteors.get(i).setExists(false);;
					projectiles.get(i).setExists(false);;
				}
			}
		}
	}
	
	public void setCoordinates(Coordinates c){
		this.c = c;
	}
}
