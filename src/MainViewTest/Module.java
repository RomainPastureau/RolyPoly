package MainViewTest;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class Module implements Serializable {
	
	protected int x, y, width, height;
	protected Window window;
	
	public Module(Window w){
		this.window = w;
		this.x = window.getX();
		this.y = window.getY();
		this.width = window.getWidth();
		this.height = window.getHeight();
	}
	
	public void paintComponent(Graphics g2d){
	}
	
	public void updateSize(){
		this.x = window.getX();
		this.y = window.getY();
		this.width = window.getWidth();
		this.height = window.getHeight();
	}
	
	public void changeWindow(Window w){
		this.window = w;
	}

}
