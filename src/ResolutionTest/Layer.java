package ResolutionTest;

import java.awt.Dimension;

public abstract class Layer {
	
	int x, y;
	int width, height;
	
	public Layer(Dimension d){
		this.x = 0;
		this.y = 0;
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
	}
	
	public Layer(int x, int y, Dimension d){
		this.x = 0;
		this.y = 0;
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
	}
	
	public void afficher(){
	}

}
