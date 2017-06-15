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
									new Color(255, 0, 0), new Color(102, 153, 255), new Color(255, 204, 0),
									new Color(18, 246, 0), new Color(153, 102, 51), new Color(255, 0, 255),
									new Color(0, 204, 153), new Color(0, 0, 0), new Color(204, 153, 0),
									new Color(255, 80, 80), new Color(102, 102, 153)};
	protected boolean visible;
	
	public TouchPoint(TuioCursor cursor){
		this.cursor = cursor;
		this.visible = true;
	}
	
	public void paintComponent(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setPaint(colors[cursor.getCursorID()%20]);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		g.fillOval((int)(cursor.getX()*width)-20, (int)(cursor.getY()*height)-20, 40, 40);
		Font font = new Font("Calibri", Font.BOLD, 80);
		g.setFont(font);
		g = CenterText.center(g, ""+(cursor.getCursorID()+1), font, 80, colors[cursor.getCursorID()%20], (int)(cursor.getX()*width), (int)(cursor.getY()*height), new Dimension(80, 80));
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public boolean isVisible(){
		return(this.visible);
	}
}
