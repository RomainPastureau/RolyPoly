package AreaTest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import Shared.Layer;

public class TestLayer extends Layer implements KeyListener {
	
	protected int nbZones;
	protected Color[] tempColor = {new Color(255, 156, 0), new Color(0, 111, 225), new Color(66, 166, 0),
									new Color(128, 0, 60), new Color(255, 255, 0), new Color(0, 240, 255),
									new Color(153, 204, 0), new Color(189, 0, 236), new Color(148, 148, 148)};
	protected ArrayList<Color> colors;
	
	public TestLayer(int nbZones, Dimension d){
		super(d);
		this.nbZones = nbZones;
		this.colors = new ArrayList<Color>();
		this.colors.addAll(Arrays.asList(tempColor));
	}
	
	public TestLayer(int nbZones, int x, int y, Dimension d){
		super(x, y, d);
		this.nbZones = nbZones;
		this.colors = new ArrayList<Color>();
		this.colors.addAll(Arrays.asList(tempColor));
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(10));
		if(nbZones == 1){
			g2d.setColor(colors.get(0));
			g2d.drawRect(x+5, y+5, width-10, height-10);
		}
		if(nbZones == 2){
			for(int i = 0; i < nbZones; i++){
				g2d.setColor(colors.get(i));
				g2d.drawRect(x+5+(i*((width)/nbZones)), y+5, (width-20)/nbZones, height-10);
			}
		}
		if(nbZones == 3){
			for(int i = 0; i < nbZones; i++){
				g2d.setColor(colors.get(i));
				g2d.drawRect(x+5+(i*((width)/nbZones)), y+5, (width-30)/nbZones, height-10);
			}
		}
		if(nbZones == 4){
			for(int i = 0; i < nbZones; i++){
				g2d.setColor(colors.get(i));
				g2d.drawRect((x+5+((i*((width)/2))%(width))), 
						(y+5)+((int)(i/2)*(height/2)), 
						(width-20)/2, (height-20)/2);
			}
		}
		if(nbZones == 6){
			for(int i = 0; i < nbZones; i++){
				g2d.setColor(colors.get(i));
				g2d.drawRect(((x+5)+((i*((width)/3))%(width))), 
						(y+5)+((int)(i/3)*(height/2)), 
						(width-30)/3, (height-20)/2);
			}
		}
		if(nbZones == 8){
			for(int i = 0; i < nbZones; i++){
				g2d.setColor(colors.get(i));
				g2d.drawRect(((x+5)+((i*((width)/4))%(width))), 
						(y+5)+((int)(i/4)*(height/2)), 
						(width-40)/4, (height-20)/2);
			}
		}
		if(nbZones == 9){
			for(int i = 0; i < nbZones; i++){
				g2d.setColor(colors.get(i));
				g2d.drawRect(((x+5)+((i*((width)/3))%(width))), 
						(y+5)+((int)(i/3)*(height/3)), 
						(width-30)/3, (height-30)/3);
			}
		}

	}

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() > 96 && ke.getKeyCode() < 106){
			this.nbZones = ke.getKeyCode()-96;
		}
	}
	
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	
}
