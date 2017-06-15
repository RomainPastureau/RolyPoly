package MultiTouchTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import Shared.CenterText;
import TUIO.TuioCursor;

public class TouchPoint {

	protected TuioCursor cursor;
	protected Color[] colors = {new Color(255, 156, 0), new Color(0, 111, 225), new Color(66, 166, 0),
									new Color(128, 0, 160), new Color(255, 255, 0), new Color(0, 240, 255),
									new Color(153, 204, 0), new Color(189, 0, 236), new Color(148, 148, 148),
									new Color(255, 0, 0), new Color(245, 222, 179), new Color(255, 165, 0)};
	
	public TouchPoint(TuioCursor cursor){
		this.cursor = cursor;
	}
	
	public void paintComponent(Graphics2D g){
		g.setPaint(colors[cursor.getCursorID()%12]);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		g.fillOval((int)cursor.getX()-20, (int)cursor.getY()-20, 40, 40);
		Font font = new Font("Calibri", Font.BOLD, 80);
		g.setFont(font);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		System.out.println(cursor.getX()*width+" "+cursor.getY()*height);
		g = CenterText.center(g, ""+cursor.getCursorID(), font, 80, colors[cursor.getCursorID()%12], (int)(cursor.getX()*width), (int)(cursor.getY()*height), new Dimension(80, 80));
	}
}
