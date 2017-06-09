package ResolutionTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class CenterText {
	
	public static Graphics2D center(Graphics2D g2d, String titre, Color color, int x, int y, Dimension d){
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(titre, g2d);
		int titreX = x + (int)((d.getWidth() - r.getWidth())/2);
		int titreY = y + (int)((d.getHeight() - r.getHeight())/2 + fm.getAscent());
		g2d.setColor(color);
		g2d.drawString(titre, titreX, titreY);
		return(g2d);
	}

}
