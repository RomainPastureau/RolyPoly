package ResolutionTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Button {
	
	protected int x, y;
	protected Dimension d;
	protected Color buttonColor;
	protected boolean shadow;
	protected String titre;
	protected boolean value;
	
	public Button(int x, int y, Dimension d, Color buttonColor, boolean shadow, String titre, boolean value){
		this.x = x;
		this.y = y;
		this.d = d;
		this.buttonColor = buttonColor;
		this.shadow = shadow;
		this.titre = titre;
		this.value = value;
	}
	
	public void paintComponent(Graphics g){
		if(shadow){
			g.setColor(new Color(128, 128, 128));
			g.fillRect(x+5, y-5, (int)d.getWidth(), (int)d.getHeight());
		}
		g.setColor(buttonColor);
		g.fillRect(x, y, (int)d.getWidth(), (int)d.getHeight());
		Graphics2D g2d = (Graphics2D)g;
		Font font = new Font("Calibri", Font.ITALIC, 80);
		CenterText.center(g2d, titre, font, 80, Color.BLACK, x, y, d);
	}
	
}
