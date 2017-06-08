package ResolutionTest;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Fenetre extends JFrame {
	
	private static final long serialVersionUID = 1L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width, height;
	
	public Fenetre(){
		//Titre de fenêtre
		this.setTitle("RolyPoly Resolution Test 0.1");
		
		//Taille de la fenêtre
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		this.setSize(width, height);
		
		//Plein écran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//Action à la fermeture
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Affichage du panel
		Background bg = new Background(false, true);
		this.setContentPane(bg);
		
		//Affichage
		this.setVisible(true);
	}
}
