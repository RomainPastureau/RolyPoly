package Garbage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
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
	public BackgroundPanel(Color backgroundColor){
		this.bgCenterCol = backgroundColor;
		this.lines = false;
		this.gradient = false;
		menu = true;
	}
	
	//Création d'un fond avec les couleurs par défaut, choix
	//de la présence d'un quadrillage et/ou d'un dégradé
	public BackgroundPanel(boolean lines, boolean gradient){
		this.lines = lines;
		this.gradient = gradient;
		menu = true;
	}
	
	//Création d'un fond avec les couleurs du dégradé, des lignes
	//et l'espacement entre les lignes
	public BackgroundPanel(Color bgCenterCol, Color bgExterCol, Color lineCol, int lineSpace){
		this.bgCenterCol = bgCenterCol;
		this.bgExterCol = bgExterCol;
		this.lineCol = lineCol;
		this.lineSpace = lineSpace;
		menu = true;
	}
	
	//Fonction d'affichage du fond
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		//Ajout du dégradé s'il est présent
		if(this.gradient){
			RadialGradientPaint gp = new RadialGradientPaint(this.getWidth()/2, this.getHeight()/2, this.getWidth(), fractions, colorList);
			g2d.setPaint(gp);
		}
		else{
			g.setColor(bgCenterCol);
		}
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Ajout du quadrillage s'il est présent
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
		
		//Ajout du menu
//		if(menu){
//			HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();
//			TitlePanel tp = new TitlePanel("RolyPoly Resolution Test", options, new Dimension(this.getWidth(), this.getHeight()));
//			tp.paintComponent(g);
//		}
		
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
}
