package Shared;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageModule extends Module {
	
	protected BufferedImage img, subImg;
	protected int width, height;
	
	public ImageModule(BufferedImage img){
		this.img = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
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

	public BufferedImage getImage(){
		return(this.img);
	}
	
	public int getWidth(){
		return(this.width);
	}
	
	public int getHeight(){
		return(this.height);
	}
}
