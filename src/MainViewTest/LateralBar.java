package MainViewTest;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import Shared.ImageModule;
import Shared.Window;

public class LateralBar {
	
	protected boolean visible;
	protected int x, y, width, height;
	protected String orientation, position;
	protected long transition, now, timer;
	protected ArrayList<Window> windows;
	protected Dimension screenSize;
	protected ArrayList<ImageModule> modules;
	protected ArrayList<Integer> activeImages;
	protected int currentImage;
	private Color color = new Color(80, 80, 80, 200);
	
	public LateralBar(String orientation, String position, ArrayList<Window> windows, ArrayList<ImageModule> modules, int currentImage){
		this.currentImage = currentImage;
		this.windows = windows;
		this.orientation = orientation;
		this.position = position;
		this.now = System.currentTimeMillis();
		this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		getActiveImages();
		this.transition = 0;
		this.timer = 0;
		this.visible = true;
		this.modules = modules;
		setFenetre();
	}
	
	public void paintComponent(Graphics2D g){
		this.now = System.currentTimeMillis();
		if(transition < 500){
			transition = now-timer;
		}
		
		float opacity = 0;
		
		getActiveImages();
		int nbImages = this.activeImages.size();
		
		if(visible && transition >= 500){
			opacity = 200;
		}
		else if(!visible && transition < 500){
			opacity = 200-(float)((200./500.)*transition);
		}
		else if(visible && transition < 500){
			opacity = (float)((200./500.)*transition);			
		}
		
		g.setColor(new Color(80, 80, 80, (int)opacity));
		g.fillRoundRect(x, y, width, height, 20, 20);
		g.setColor(new Color(150, 150, 150, (int)opacity));
		if(this.orientation == "Horizontal"){
			g.fillRect(20+x+(((width-40)/9)*currentImage), y, (width-40)/9, 100);
		}
		else{
			g.fillRect(x, 20+y+(((height-40)/9)*currentImage), 100, (height-40)/9);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity/200));
		
		if(visible || !visible && transition < 500){
			for(int i = 0; i < nbImages; i++){
				ImageModule im = modules.get(activeImages.get(i));
				if(this.orientation == "Horizontal"){
					im.paintComponent(g, 20+x+(((width-40)/9)*i), y+10, (width-40)/9, 80);
					int j = 0;
					for(Window w:windows){
						if(w.getWidth() > 0 && w.getID() == i){
							g.setColor(w.getColor());
							g.fillRect(20+x+(((width-40)/9)*i)+((j%3)*25)+(((width-40)/9)/2)-35, y+15+((int)(j/3)*25), 20, 20);
							j++;
						}
					}
				}
				else{
					im.paintComponent(g, x+10, 20+y+(((height-40)/9)*i), 80, (height-40)/9);
					int j = 0;
					for(Window w:windows){
						if(w.getWidth() > 0 && w.getID() == i){
							g.setColor(w.getColor());
							g.fillRect(x+15+((j%3)*25), 20+y+(((height-40)/9)*i)+((int)(j/3)*25)+(((height-40)/9)/2)-35, 20, 20);
							j++;
						}
					}
				}
			}
		}
		

	}
	
	public void update(ArrayList<Window> windows, int currentImage){
		this.windows = windows;
		this.currentImage = currentImage;
	}
	
	public void getActiveImages(){
		this.activeImages = new ArrayList<Integer>();
			for(Window w:windows){
				int ID = w.getID();
				if(!this.activeImages.contains(ID) && w.getWidth() != 0){
					this.activeImages.add(ID);
				}
			}
		Collections.sort(this.activeImages);
		}
	
	public void setFenetre(){
		if(this.orientation == "Vertical"){
			this.width = 100;
			this.height = (int)screenSize.getHeight() - 100;
			this.y = 50;
			if(this.position == "Gauche"){
				this.x = 50;
			}
			else{
				this.x = (int)screenSize.getWidth() - 150;
			}
		}
		else{
			this.width = (int)screenSize.getWidth() - 100;
			this.height = 100;
			this.x = 50;
			if(this.position == "Haut"){
				this.y = 50;
			}
			else{
				this.y = (int)screenSize.getHeight() - 150;
			}
		}
	}
	
	public void setVisible(){
		this.visible = !this.visible;
		transition = 0;
		timer = now;
	}
	
	public void setPosition(String position){
		this.position = position;
		setFenetre();
	}
	
	public void setOrientation(String orientation){
		this.orientation = orientation;
		setFenetre();
	}
	
	public int selectImage(MouseEvent e){
		for(int i = 0; i < activeImages.size(); i++){
			if(this.orientation == "Horizontal"){
				if(20+x+(((width-40)/9)*i) < e.getX() && e.getX() < 20+x+(((width-40)/9)*(i+1)) && y < e.getY() && e.getY() < y+100){
					for(Window w:windows){
						w.setInactive();
					}
					currentImage = activeImages.get(i);
					return(currentImage);
				}
			}
			else{
				if(x < e.getX() && e.getX() < x+100 && 20+y+(((height-40)/9)*i) < e.getY() && e.getY() < 20+y+(((height-40)/9)*(i+1))){
					for(Window w:windows){
						w.setInactive();
					}
					currentImage = activeImages.get(i);
					return(currentImage);
				}
			}
		}
		return(currentImage);
	}

}
