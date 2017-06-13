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
	protected long time;
	protected boolean checktime;
	
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
		this.checktime = true;
	}
	
	public void paintComponent(Graphics g) throws InterruptedException{
		if(circle.clicked){
			circle.paintComponent(g);
			this.nextOne = true;
			if(checktime){
				time = System.currentTimeMillis();
				checktime = false;
			}
		}
		if(nextOne && time+1000 < System.currentTimeMillis() && i < nbZones){
			System.out.println("Affichage du cercle n°"+(i+1));
			int tailleZone = ThreadLocalRandom.current().nextInt(tailleMin, tailleMax + 1);
			int x = ThreadLocalRandom.current().nextInt(0, width-tailleZone);
			int y = ThreadLocalRandom.current().nextInt(0, height-tailleZone);
			circle = new CircleZone(x, y, tailleZone);
			i++;
			nextOne = false;
			checktime = true;
		}
		
		else if(nextOne && time+1000 < System.currentTimeMillis() && i == nbZones){
			i++;
			nextOne = false;
			checktime = true;
			over = true;
		}
		circle.paintComponent(g);

	}

	public void mouseClicked(MouseEvent e) {
		circle.mouseClicked(e);
	}
	
	public boolean getOver(){
		return(over);
	}
	
	public void setOver(boolean over){
		this.over = over;
	}
	
	public void reboot(){
		this.over = false;
		this.i = 0;
		this.nextOne = true;
		this.checktime = true;
		this.time = 0;
		this.circle.clicked = false;
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
