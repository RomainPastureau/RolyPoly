package AreaTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JPanel;

import Shared.BackgroundLayer;
import Shared.TitleLayer;

public class AreaTestPanel extends JPanel implements MouseListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	protected int x, y;
	protected Dimension d;
	protected BackgroundLayer bg;
	protected TitleLayer tl;
	protected HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();
	protected TestLayer test;
	protected boolean menu;
	
	public AreaTestPanel(Dimension d){
		this.x = 0;
		this.y = 0;
		this.d = d;
		this.bg = new BackgroundLayer(true, true, d);
		Color mainColor = new Color(153, 204, 0);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly Area Test", "1.0", options, colors, 0.5f, d);
		this.test = new TestLayer(2, d);
		this.menu = true;
	}
	
	public AreaTestPanel(int x, int y, Dimension d){
		this.x = x;
		this.y = y;
		this.d = d;
		this.bg = new BackgroundLayer(true, true, d);
		Color mainColor = new Color(153, 204, 0);
		Color[] tempCol = {mainColor.brighter(), mainColor, mainColor.darker()};
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.addAll(Arrays.asList(tempCol));
		this.tl = new TitleLayer("RolyPoly Area Test", "1.0", options, colors, 0.5f, d);
		this.test = new TestLayer(2, d);
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
//			try{
				test.paintComponent(g);
//				if(test.getOver() == true){
//					menu = true;
//					test.reboot();
//				}
//			} catch(InterruptedException ex){
//				ex.printStackTrace();
//			}
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

	public void keyPressed(KeyEvent ke) {
		if(menu){}
		else{
			test.keyPressed(ke);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		test.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		test.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
