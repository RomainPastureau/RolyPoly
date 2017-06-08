package ResolutionTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;

import javax.swing.JPanel;

public class Background extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	int lineSpace = 100;
	
	//Couleurs par défaut
	Color bgCenterCol = new Color(234, 242, 255);
	Color bgExterCol = new Color(201, 221, 252);
	Color lineCol = new Color(201, 221, 252);
	Color[] colorList = {bgCenterCol, bgExterCol};
	
	float[] fractions = {0.0f, 0.9f};	
	boolean lines, gradient;
	
	public Background(Color backgroundColor){
		this.bgCenterCol = backgroundColor;
		this.lines = false;
		this.gradient = false;
	}
	
	public Background(boolean lines, boolean gradient){
		this.lines = lines;
		this.gradient = gradient;
	}
	
	public Background(Color bgCenterCol, Color bgExterCol, Color lineCol, int lineSpace){
		this.bgCenterCol = bgCenterCol;
		this.bgExterCol = bgExterCol;
		this.lineCol = lineCol;
		this.lineSpace = lineSpace;
	}
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2d = (Graphics2D)g;
		if(this.gradient){
			RadialGradientPaint gp = new RadialGradientPaint(this.getWidth()/2, this.getHeight()/2, this.getWidth(), fractions, colorList);
			g2d.setPaint(gp);
		}
		else{
			g.setColor(bgCenterCol);
		}
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if(this.lines){
			g.setColor(lineCol);
			
			//Traits verticaux
			for(int i = 0; i <= this.getWidth()/lineSpace; i++){
				g.drawLine(i*lineSpace, 0, i*lineSpace, this.getHeight());
			}
			
			//Traits horizontaux
			for(int i = 0; i <= this.getHeight()/lineSpace; i++){
				g.drawLine(0, i*lineSpace, this.getWidth(), i*lineSpace);
			}
		}
		
	}
	
	public void setGradient(Color bgCenterCol, Color bgExterCol){
		this.bgCenterCol = bgCenterCol;
		this.bgExterCol = bgExterCol;
	}
}
