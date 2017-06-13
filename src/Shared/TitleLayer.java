package Shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TitleLayer extends Layer implements MouseListener {
	
	protected String titre = "<Pas de titre>", version = "0.0";
	protected HashMap<String, ArrayList<String>> options;
	protected ArrayList<Color> colors;
	protected float opacity = 0.5f;
	protected Button boutonLancer;
	protected boolean menu;
	
	public TitleLayer(String titre, String version, HashMap<String, ArrayList<String>> options, Dimension d){
		super(d);
		this.titre = titre;
		this.version = version;
		this.options = options;
		this.colors = new ArrayList<Color>();
		Color mainColor = new Color(255, 204, 0);
		Color mainColorBrighter = new Color(255, 230, 128);
		Color mainColorDarker = new Color(255, 153, 0);
		Color[] tempCol = {mainColorBrighter, mainColor, mainColorDarker};
		this.colors.addAll(Arrays.asList(tempCol));
		boutonLancer = new Button(width/2-width/6, height/2+height/4, new Dimension(width/3, height/10), colors.get(0), "Démarrer", true);
	}
	
	public TitleLayer(String titre, String version, HashMap<String, ArrayList<String>> options, Color mainColor, float opacity, Dimension d){
		super(d);
		this.titre = titre;
		this.version = version;
		this.options = options;
		this.colors = new ArrayList<Color>();
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		this.colors.addAll(Arrays.asList(tempCol));
		this.opacity = opacity;
		boutonLancer = new Button(width/2-width/6, height/2+height/4, new Dimension(width/3, height/10), colors.get(0), "Démarrer", true);
	}
	
	public TitleLayer(String titre, String version, HashMap<String, ArrayList<String>> options, ArrayList<Color> colors, float opacity, Dimension d){
		super(d);
		this.titre = titre;
		this.version = version;
		this.options = options;
		this.colors = colors;
		this.opacity = opacity;
		boutonLancer = new Button(width/2-width/6, height/2+height/4, new Dimension(width/3, height/10), colors.get(0), "Démarrer", true);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(255, 255, 255, (int)(255*opacity)));
		g.fillRect(0, 0, width, height);
		g.setColor(this.colors.get(1));
		g.fillRect(0, 200, width, 200);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Font font = new Font("Calibri", Font.BOLD, 120);
		g2d = CenterText.center(g2d, titre, font, 120, Color.BLACK, 0, 200, new Dimension(width, 200));
		g2d = CenterText.center(g2d, "Version "+version, font, 40, Color.BLACK, width-300, height-60, new Dimension(300,60));
		
		boutonLancer.paintComponent(g);	
		menu = boutonLancer.getValue();
	}

	public void mouseClicked(MouseEvent e) {
		boutonLancer.mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {
		boutonLancer.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		boutonLancer.mouseExited(e);
	}

	public void mousePressed(MouseEvent e) {
		boutonLancer.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		boutonLancer.mouseReleased(e);
	}
	
	public boolean getMenu(){
		return(menu);
	}
	
	public void setMenu(boolean menu){
		this.menu = menu;
		this.boutonLancer.setValue(menu);
	}
}
