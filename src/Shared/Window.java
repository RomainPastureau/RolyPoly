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
	protected long time, now, currentTime;
	protected boolean canChangeColor;
	protected int moduleID;
	protected int startX, startY, lastStartX, lastStartY, lastMainX, lastMainY;
	protected float ratio;
	
	public Window(int id, int x, int y, int width, int height, Color color, int moduleID, float ratio){
		this.id = id; //Identifiant de la fenêtre (entre 1 et 9)
		this.moduleID = moduleID; //Identifiant du module, càd de l'image assignée à la fenêtre (entre 1 et 9)
		
		//Position de la fenêtre (SplitView)
		this.x = x; 
		this.y = y;
		
		//Position de la fenêtre (MainView)
		this.mainX = 0;
		this.mainY = 0;
		
		//Taille de la fenêtre (SplitView)
		this.width = width;
		this.height = height;
		
		//Taille de la fenêtre (MainView)
		this.mainWidth = (int)(ratio*width);
		this.mainHeight = (int)(ratio*height);
		
		//Ratio de l'image dans la fenêtre (width/height de l'image)
		this.ratio = ratio;

		//Couleur de la fenêtre
		this.color = color;
		
		//Position de la fenêtre sur l'image
		this.startX = 0;
		this.startY = 0;
		
		//Position lors du clic sur l'image
		this.lastMainX = 0;
		this.lastMainY = 0;
		
		//Position lors du clic sur l'image
		this.lastStartX = 0;
		this.lastStartY = 0;
		
		//Définit si l'image déplacée est l'image courante
		this.current = false;
		this.active = false;
		
		//Variables de fondu d'activation		
		this.time = 0;
		this.now = 2000;
		this.canChangeColor = false;
		this.currentTime = System.currentTimeMillis();
	}
	
	//MÉTHODES - Général
	
	//Renvoie true si les coordonnées sont dans la fenêtre
	public boolean isInside(int x, int y){
		
		//Vérifie que les coordonnées soient bien dans la fenêtre
		if(this.x < x && x < this.x+width && this.y < y && y < this.y+height){
			//Si ce n'est pas la fenêtre courante, on affiche un dégradé
			if(!this.current){
				this.canChangeColor = true;
				this.time = currentTime;
				this.now = 0;
			}
			//Sinon on n'affiche pas le dégradé
			else if(this.now >= 2000){
				this.canChangeColor = false;
			}
			
			this.current = true;
			this.active = true;
			
			//On met à jour le point de contact
			this.contactX = x;
			this.contactY = y;
			
			return(true);
		}
		//
		this.current = false;
		this.active = false;
		return(false);
	}
	
	public boolean isInsideMain(int x, int y, int currentImage){
		//Vérifie que les coordonnées soient bien dans la fenêtre et que le module courant soit bien celui affiché
		if(this.mainX < x && x < this.mainX+mainWidth && this.mainY < y && y < this.mainY+mainHeight && this.moduleID == currentImage){
			//Si ce n'est pas la fenêtre courante, on affiche un dégradé
			if(!this.current){
				this.canChangeColor = true;
				this.time = currentTime;
				this.now = 0;
			}
			//Sinon on n'affiche pas le dégradé
			else if(this.now >= 2000){
				this.canChangeColor = false;
			}
			
			this.current = true;
			this.active = true;
			
			//On met à jour le point de contact
			this.contactX = x;
			this.contactY = y;
			
			return(true);
		}
		this.current = false;
		this.active = false;
		return(false);
	}
	
	//Mise à jour des coordonées lorsque la souris est relachée (SplitView)
	public void updateCorner(){
		this.lastStartX = this.startX;
		this.lastStartY = this.startY;
	}
	
	//Mise à jour des coordonnées lorsque la souris est relachée (MainView)
	public void updateCornerMain(){
		this.lastMainX = this.mainX;
		this.lastMainY = this.mainY;
	}
	
	//MÉTHODES - Affichage
	
	public void paintComponent(Graphics2D g2d){
		paintComponent(g2d, x, y, width, height);
	}
	
	public void paintComponentMain(Graphics2D g2d){
		paintComponent(g2d, mainX, mainY, mainWidth, mainHeight);
	}
	
	private void paintComponent(Graphics2D g2d, int x, int y, int width, int height){
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawRect(x, y, width, height);
		if(canChangeColor){
			if(now < 2000){
				now = currentTime-time;
			}
			if(now >= 2000){
				now = 2000;
				canChangeColor = false;
			}
		}
		g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(128 - ((now/1000.)*64))));
		g2d.fillRect(x, y, width, height);  
	}
	
	//MÉTHODES - Getters/Setters
	public void setPosition(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setMainPosition(int x, int y, int width, int height){
		this.mainX = x;
		this.mainY = y;
		this.mainWidth = width;
		this.mainHeight = height;
	}
	
	public void setModuleID(int moduleID){
		this.moduleID = moduleID;
	}
	
	public int getModuleID(){
		return(this.moduleID);
	}
	
	public Color getColor(){
		return(this.color);
	}
	
	public void setInactive(){
		this.active = false;
	}
	
	public void setActive(){
		this.active = true;
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
	
	public int getLastMainX(){
		return(this.lastMainX);
	}
	
	public int getLastMainY(){
		return(this.lastMainY);
	}
	
	public int getMainX(){
		return(this.mainX);
	}
	
	public int getMainY(){
		return(this.mainY);
	}
	
	public int getMainWidth(){
		return(this.mainWidth);
	}
	
	public int getMainHeight(){
		return(this.mainHeight);
	}
	
	public void setStartX(int startX){
		this.startX = startX;
	}
	
	public void setStartY(int startY){
		this.startY = startY;
	}
	
	public void setMainX(int mainX, ImageModule img){
		this.mainX = mainX;
		this.startX = (int)((mainX - img.getStartX())/ratio);
	}
	
	public void setMainY(int mainY, ImageModule img){
		this.mainY = mainY;
		this.startY = (int)((mainY - img.getStartY())/ratio);
	}
	
	public void update(float ratio, int imgStartX, int imgStartY){
		this.ratio = ratio;
		this.mainWidth = (int)(width*ratio);
		this.mainHeight = (int)(height*ratio);
		this.mainX = (int)(startX*ratio)+imgStartX; 
		this.mainY = (int)(startY*ratio)+imgStartY; 
	}
	
}
