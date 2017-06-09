package Garbage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	protected String titre = "<Pas de titre>";
	protected HashMap<String, ArrayList<String>> options;
	protected ArrayList<Color> colors = new ArrayList<Color>();
	protected float opacity = 0.5f;
	protected int width;
	protected int height;
	
	public TitlePanel(String titre, HashMap<String, ArrayList<String>> options, Dimension d){
		this.titre = titre;
		this.options = options;
		Color mainColor = new Color(255, 204, 0);
		Color mainColorBrighter = new Color(255, 230, 128);
		Color mainColorDarker = new Color(255, 153, 0);
		Color[] tempCol = {mainColorBrighter, mainColor, mainColorDarker};
		this.colors.addAll(Arrays.asList(tempCol));
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
	}
	
	public TitlePanel(String titre, HashMap<String, ArrayList<String>> options, Color mainColor, float opacity, Dimension d){
		this.titre = titre;
		this.options = options;
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		this.colors.addAll(Arrays.asList(tempCol));
		this.opacity = opacity;
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
	}
	
	public TitlePanel(String titre, HashMap<String, ArrayList<String>> options, ArrayList<Color> colors, float opacity, Dimension d){
		this.titre = titre;
		this.options = options;
		this.colors = colors;
		this.opacity = opacity;
		this.width = (int)d.getWidth();
		this.height = (int)d.getHeight();
	}
	
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		g.setColor(new Color(255, 255, 255, (int)(255*opacity)));
		g.fillRect(0, 0, width, height);
		g.setColor(this.colors.get(1));
		g.fillRect(0, 200, width, 200);
//		g.setColor(new Color(255, 0, 0));
//		Font font = new Font("Calibri", Font.BOLD, 120);
//		FontMetrics metrics = g.getFontMetrics();
//		int x = (width - metrics.stringWidth(titre))/2;
//		int y = 200 + ((200 - metrics.getHeight())/2) + metrics.getAscent();
//		g.setFont(font);
//		g.drawString(titre, x, y);
	}
}
