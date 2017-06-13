package ResolutionTest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import Shared.BackgroundLayer;
import Shared.TestLayer;
import Shared.TitleLayer;

public class ResolutionTestPanel extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	protected int x, y;
	protected Dimension d;
	protected BackgroundLayer bg;
	protected TitleLayer tl;
	protected TestLayer test;
	protected HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();
	protected boolean menu;
	
	public ResolutionTestPanel(Dimension d){
		this.x = 0;
		this.y = 0;
		this.d = d;
		this.bg = new BackgroundLayer(true, true, d);
		this.tl = new TitleLayer("RolyPoly Resolution Test", options, d);
		this.test = new TestLayer(10, 5, 150, d);
		this.menu = true;
	}
	
	public ResolutionTestPanel(int x, int y, Dimension d){
		this.x = x;
		this.y = y;
		this.d = d;
		this.bg = new BackgroundLayer(true, true, d);
		this.tl = new TitleLayer("RolyPoly Resolution Test", options, d);
		this.test = new TestLayer(10, 5, 150, d);
		this.menu = true;
	}
	
	public void paintComponent(Graphics g){
		bg.paintComponent(g);
		if(menu){
			tl.paintComponent(g);
			if(tl.getMenu() == false){
				menu = false;
				tl.setMenu(true);
			}
		}
		else{
			try{
				test.paintComponent(g);
				if(test.getOver() == true){
					menu = true;
					test.reboot();
				}
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		bg.mouseClicked(e);
		if(menu){
			tl.mouseClicked(e);	
		}
		else{
			test.mouseClicked(e);
		}
		
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
