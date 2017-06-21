package MainViewTest;

import java.awt.image.BufferedImage;

public class ImageModule extends Module {

	protected BufferedImage img;
	
	public ImageModule(BufferedImage img, Window window){
		super(window);
		this.img = img;
	}
	

	
}
