package SplitFocusTest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ImageModule extends Module implements Serializable {

	protected BufferedImage img, subImg;
	protected int startX, startY, lastStartX, lastStartY;
	
	public ImageModule(BufferedImage img, Window window){
		super(window);
		this.img = img;
		this.startX = 0;
		this.startY = 0;
		this.lastStartX = 0;
		this.lastStartY = 0;
	}
	
	public void paintComponent(Graphics2D g){
		if(width != 0 && height != 0 && startX >= 0 && startY >= 0 && startX+width <= img.getWidth() && startY+height <= img.getHeight()){
			this.subImg = img.getSubimage(startX, startY, width, height);
			g.drawImage(subImg, null, x, y);
		}
		else{
			g.drawImage(img, x, y, null);
		}
	}
	
	public void updateCorner(){
		this.lastStartX = this.startX;
		this.lastStartY = this.startY;
	}
	
	public void move(int a, int b){
		if(window.active){
			this.startX = this.lastStartX-(a-this.window.getContactX());
			this.startY = this.lastStartY-(b-this.window.getContactY());
		}
		if(startX > img.getWidth()-width){
			this.startX = img.getWidth()-width;
		}
		if(startX < 0){
			this.startX = 0;
		}
		if(startY > img.getHeight()-height){
			this.startY = img.getHeight()-height;
		}
		if(startY < 0){
			this.startY = 0;
		}
	}
	
	public int getStartX(){
		return(this.startX);
	}
	
	public int getStartY(){
		return(this.startY);
	}
	
	public BufferedImage getImage(){
		return(this.img);
	}
	
	public Window getWindow(){
		return(this.window);
	}
}
