package Shared;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ImageModule extends Module {
	
	protected BufferedImage img, subImg;
	protected int width, height;
	protected float ratio, screenRatio;
	protected int screenWidth, screenHeight, resizedWidth, resizedHeight;
	
	public ImageModule(BufferedImage img){
		this.img = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.ratio = (float)img.getWidth()/(float)img.getHeight();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.screenWidth = (int)screenSize.getWidth();
		this.screenHeight = (int)screenSize.getHeight();
		this.screenRatio = (float)(screenSize.getWidth()/screenSize.getHeight());
		if(width > screenWidth || height > screenHeight){
			if(ratio > screenRatio){
				this.resizedWidth = screenWidth;
				this.resizedHeight = (int)(screenWidth/ratio);
			}
			else{
				this.resizedHeight = screenHeight; 
				this.resizedWidth = (int)(screenHeight*ratio);
			}
		}
		else{
			this.resizedWidth = width;
			this.resizedHeight = height;
		}
	}
	
	public void paintComponent(Graphics2D g, Window w){
		if(w.getWidth() != 0 && w.getHeight() != 0 && w.startX >= 0 && w.startY >= 0 && w.startX+w.getWidth() <= img.getWidth() && w.startY+w.getHeight() <= img.getHeight()){
			this.subImg = img.getSubimage(w.startX, w.startY, w.getWidth(), w.getHeight());
			g.drawImage(subImg, null, w.getX(), w.getY());
		}
		else{
			g.drawImage(img, w.getX(), w.getY(), null);
		}
	}
	
	public void paintComponent(Graphics2D g){
		g.drawImage(img, this.screenWidth/2-this.resizedWidth/2, this.screenHeight/2-this.resizedHeight/2, resizedWidth, resizedHeight, null);
	}
	
	public void paintComponent(Graphics2D g, int x, int y, int width, int height){
		float imageRatio = (float)width/(float)height;
		if(ratio > imageRatio){
			g.drawImage(img, x, y+(int)((height/2)-((width/ratio)/2)), width, (int)(width/ratio), null);
		}
		else{
			g.drawImage(img, x+(int)((width/2)-((height*ratio)/2)), y, (int)(height*ratio), height, null);
		}
	}
	
	public String toString(){
		return("Screen ratio : "+screenRatio+" - Image ratio : "+ratio+" - Image width : "+width+" - Image height : "+height+" - Resized width : "+resizedWidth+" - Resized height : "+resizedHeight);
	}

	public BufferedImage getImage(){
		return(this.img);
	}
	
	public int getWidth(){
		return(this.width);
	}
	
	public int getHeight(){
		return(this.height);
	}
	
	public Dimension getSize(){
		return(new Dimension(this.width, this.height));
	}
	
	public Dimension getResized(){
		return(new Dimension(this.resizedWidth, this.resizedHeight));
	}
	
	public int getResizedWidth(){
		return(this.resizedWidth);
	}
	
	public int getResizedHeight(){
		return(this.resizedHeight);
	}
	
	public float getRatio(){
		float ratio = (float)this.resizedWidth/(float)this.width;
		if(ratio <= 1){
			return(ratio);
		}
		return(1.f);
	}
	
	public int getStartX(){
		return(this.screenWidth/2-this.resizedWidth/2);
	}
	
	public int getStartY(){
		return(this.screenHeight/2-this.resizedHeight/2);
	}
}
