package Shared;

import java.awt.Dimension;

public abstract class Layer {
	
	protected int x, y;
	protected int width;
	protected int height;
	
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
