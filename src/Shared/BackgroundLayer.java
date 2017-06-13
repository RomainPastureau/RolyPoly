package Shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BackgroundLayer extends Layer implements MouseListener {
	
	//Espacement entre deux lignes du quadrillage
	protected int lineSpace = 100;
	
	//Couleurs par défaut
	protected Color bgCenterCol = new Color(234, 242, 255);
	protected Color bgExterCol = new Color(201, 221, 252);
	protected Color lineCol = new Color(201, 221, 252);
	protected Color[] colorList = {bgCenterCol, bgExterCol};
	
	protected float[] fractions = {0.0f, 0.9f};	
	protected boolean lines, gradient;
	protected boolean menu;
	
	//Création d'un fond avec une couleur unique
	public BackgroundLayer(Color backgroundColor, Dimension d){
		super(d);
		this.bgCenterCol = backgroundColor;
		this.lines = false;
		this.gradient = false;
	}
	
	//Création d'un fond avec les couleurs par défaut, choix
	//de la présence d'un quadrillage et/ou d'un dégradé
	public BackgroundLayer(boolean lines, boolean gradient, Dimension d){
		super(d);
		this.lines = lines;
		this.gradient = gradient;
	}
	
	//Création d'un fond avec les couleurs du dégradé, des lignes
	//et l'espacement entre les lignes
	public BackgroundLayer(Color bgCenterCol, Color bgExterCol, Color lineCol, int lineSpace, Dimension d){
		super(d);
		this.bgCenterCol = bgCenterCol;
		this.bgExterCol = bgExterCol;
		this.lineCol = lineCol;
		this.lineSpace = lineSpace;
	}
	
	//Fonction d'affichage du fond
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		//Ajout du dégradé s'il est présent
		if(this.gradient){
			RadialGradientPaint gp = new RadialGradientPaint(width/2, height/2, width, fractions, colorList);
			g2d.setPaint(gp);
		}
		else{
			g.setColor(bgCenterCol);
		}
		g.fillRect(x, y, width, height);
		
		//Ajout du quadrillage s'il est présent
		if(this.lines){
			g.setColor(lineCol);
			
			//Traits verticaux
			for(int i = 0; i <= this.width/lineSpace; i++){
				g.drawLine(i*lineSpace, y, i*lineSpace, this.height);
			}
			
			//Traits horizontaux
			for(int i = 0; i <= this.height/lineSpace; i++){
				g.drawLine(x, i*lineSpace, width, i*lineSpace);
			}
		}
		
	}

	//Réglage du dégradé
	public void setGradient(Color bgCenterCol, Color bgExterCol){
		this.bgCenterCol = bgCenterCol;
		this.bgExterCol = bgExterCol;
	}
	
	//Réglage de l'espacement entre les lignes du quadrillage
	public void setLineSpace(int lineSpace){
		this.lineSpace = lineSpace;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
