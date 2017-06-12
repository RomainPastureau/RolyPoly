package ResolutionTest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

public class TestLayer extends Layer implements MouseListener {
	
	protected int nbZones;
	protected int tailleMin, tailleMax;
	protected boolean nextOne;
	protected CircleZone circle;
	protected boolean over;
	protected int i;
	protected int randomNum;
	
	public TestLayer(int nbZones, int tailleMin, int tailleMax, Dimension d){
		super(d);
		this.nbZones = nbZones;
		this.tailleMin = tailleMin;
		this.tailleMax = tailleMax;
		this.nextOne = true;
		this.over = false;
		this.i = 0;
		this.randomNum = ThreadLocalRandom.current().nextInt(tailleMin, tailleMax + 1);
		this.circle = new CircleZone(0, 0, 0);
	}
	
	public void paintComponent(Graphics g) throws InterruptedException{
		if(circle.clicked){
			circle.paintComponent(g);
			this.nextOne = true;
		}
		if(nextOne){
			int tailleZone = ThreadLocalRandom.current().nextInt(tailleMin, tailleMax + 1);
			int x = ThreadLocalRandom.current().nextInt(0, width-tailleZone);
			int y = ThreadLocalRandom.current().nextInt(0, height-tailleZone);
			circle = new CircleZone(x, y, tailleZone);
			i++;
			nextOne = false;
		}
		circle.paintComponent(g);

		if(i == nbZones){
			over = true;
		}
	}

	public void mouseClicked(MouseEvent e) {
		circle.mouseClicked(e);
		//this.nextOne = circle.getClicked();
	}
	
	public boolean getOver(){
		return(over);
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
