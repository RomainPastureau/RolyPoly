package AreaTest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageModule extends Module {

	protected BufferedImage img, subImg;
	protected int startX, startY;
	
	public ImageModule(BufferedImage img, Window window){
		super(window);
		this.img = img;
		this.startX = 0;
		this.startY = 0;
	}
	
	public void paintComponent(Graphics2D g){
		if(width != 0 && height != 0 && startX+width < img.getWidth() && startY+height < img.getHeight()){
			this.subImg = img.getSubimage(startX, startY, width, height);
			g.drawImage(subImg, null, x, y);
		}
		else{
			g.drawImage(img, x, y, null);
		}
	}
	
}
