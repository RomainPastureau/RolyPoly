package ResolutionTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TitleLayer extends Layer {
	
	protected String titre = "<Pas de titre>";
	protected HashMap<String, ArrayList<String>> options;
	protected ArrayList<Color> colors = new ArrayList<Color>();
	protected float opacity = 0.5f;
	
	public TitleLayer(String titre, HashMap<String, ArrayList<String>> options, Dimension d){
		super(d);
		this.titre = titre;
		this.options = options;
		Color mainColor = new Color(255, 204, 0);
		Color mainColorBrighter = new Color(255, 230, 128);
		Color mainColorDarker = new Color(255, 153, 0);
		Color[] tempCol = {mainColorBrighter, mainColor, mainColorDarker};
		this.colors.addAll(Arrays.asList(tempCol));
	}
	
	public TitleLayer(String titre, HashMap<String, ArrayList<String>> options, Color mainColor, float opacity, Dimension d){
		super(d);
		this.titre = titre;
		this.options = options;
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		this.colors.addAll(Arrays.asList(tempCol));
		this.opacity = opacity;
	}
	
	public TitleLayer(String titre, HashMap<String, ArrayList<String>> options, ArrayList<Color> colors, float opacity, Dimension d){
		super(d);
		this.titre = titre;
		this.options = options;
		this.colors = colors;
		this.opacity = opacity;
	}
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(255, 255, 255, (int)(255*opacity)));
		g.fillRect(0, 0, width, height);
		g.setColor(this.colors.get(1));
		g.fillRect(0, 200, width, 200);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Font font = new Font("Calibri", Font.BOLD, 120);
		g2d.setFont(font);
		g2d = CenterText.center(g2d, titre, Color.BLACK, 0, 200, new Dimension(width, 200));
		
	}
}
