package MultiTouchTest;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Main {

	public static void main(String[] args) { 
		//Récupération des écrans
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		GraphicsDevice gd = gs[-1];
		GraphicsConfiguration gk = gd.getDefaultConfiguration();
		
		
		Fenetre fenetre = new Fenetre(gk); 
		fenetre.setVisible(true);
		fenetre.go();
	}
}
