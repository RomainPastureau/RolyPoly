package DualScreenTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;

public class Meteor implements Serializable {
	
	protected int x, y, taille, speed;
	protected int startX, startY;
	protected boolean exists;
	protected long timeCreated;
	protected String type;
	protected Dimension d;

	public Meteor(int x, int y, int taille, int speed, String type, Dimension d){
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.taille = taille;
		this.speed = speed;
		this.exists = true;
		this.type = type;
		this.timeCreated = System.currentTimeMillis();
		this.d = d;
	}
	
	public void paintComponent(Graphics2D g, long now){
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if(this.exists && this.type.equals("Serveur")){
			double timeNow = (now - this.timeCreated)/(1000.0);
			this.y = this.startY + (int)(timeNow*this.speed);
		}
		g.setColor(new Color(189, 0, 236));
		if(this.type.equals("Serveur")){
			g.fillOval(x, y, taille, taille);
		}
		else{
			int perspectiveTaille = (int)(taille*(y/d.getHeight())*2);
			g.fillOval(x, (int)d.getHeight()/2-perspectiveTaille/2, perspectiveTaille, perspectiveTaille);
		}
	}
	
	public boolean checkIfTouches(Projectile p){
		if(this.x < p.x && p.x < this.x+taille && this.y < p.y && p.y < this.y+taille && p.exists && this.exists){
			this.exists = false;
			return(true);
		}
		return(false);
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setExists(boolean exists){
		this.exists = exists;
	}
	
	public String toString(){
		return("X: "+x+" - Y: "+y);
	}
}
