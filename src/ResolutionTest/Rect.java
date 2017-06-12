package ResolutionTest;

import java.awt.Dimension;

public class Rect {
	
	protected int positionX, positionY;
	protected int width, height;
	
	public Rect(){
		this.positionX = 0;
		this.positionY = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public Rect(int width, int height){
		this.positionX = 0;
		this.positionY = 0;
		this.width = width;
		this.height = height;
	}
	
	public Rect(int positionX, int positionY, int width, int height){
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;		
	}
	
	//Position
	public void setPosition(int positionX, int positionY){
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public void setPositionX(int positionX){
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY){
		this.positionY = positionY;
	}
	
	public int getPositionX(){
		return(this.positionX);
	}
	
	public int getPositionY(){
		return(this.positionX);
	}
	
	//Taille
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;	
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public int getWidth(){
		return(this.width);
	}
	
	public int getHeight(){
		return(this.height);
	}
	
	public Dimension getDimension(){
		return(new Dimension(this.width, this.height));
	}
	
}
