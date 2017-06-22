package MainViewTest;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ImageMainView {
	
	protected BufferedImage img;
	protected float ratio, screenRatio;
	protected int screenWidth, screenHeight, width, height, resizedWidth, resizedHeight;
	
	public ImageMainView(ImageModule im){
		this.img = im.img;
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
	
	public void paintComponent(Graphics2D g){
		g.drawImage(img, this.screenWidth/2-this.resizedWidth/2, this.screenHeight/2-this.resizedHeight/2, resizedWidth, resizedHeight, null);
	}
	
	public String toString(){
		return("Screen ratio : "+screenRatio+" - Image ratio : "+ratio+" - Image width : "+width+" - Image height : "+height+" - Resized width : "+resizedWidth+" - Resized height : "+resizedHeight);
	}
	
	public BufferedImage getImage(){
		return(this.img);
	}
	
	public Dimension getSize(){
		return(new Dimension(this.width, this.height));
	}
	
	public Dimension getResized(){
		return(new Dimension(this.resizedWidth, this.resizedHeight));
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
