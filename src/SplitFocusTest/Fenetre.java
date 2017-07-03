package SplitFocusTest;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import Shared.PressKey;
import TUIO.TuioBlob;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class Fenetre extends JFrame implements MouseListener, KeyListener, TuioListener {
	
	//VARIABLES
	private static final long serialVersionUID = 1L;
	
	//Général
	protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Taille de la fenêtre
	protected int width, height; //Taille de la fenêtre
	protected SplitFocusTestPanel sft; //Panel JFrame
	
	//Entrées souris/tactiles/clavier
	protected PressKey pk; //Permet de fermer la fenêtre
	protected TuioClient client; //Permet de récupérer le multitouch
	
	//Communication
	protected volatile ServerSocket serveurSocket; 
	protected volatile Socket clientSocket;
	protected volatile ObjectOutputStream oos;
	protected volatile ObjectInputStream ois;
	
	//Booléens et String divers
	protected volatile boolean moves; //True si une action est effectuée sur la fenêtre en cours, false sinon
	protected volatile boolean alive; //True si le programme n'a pas été fermé
	protected volatile boolean ctrlHere, ctrlThere; //True si un controle est effectué sur la SplitView/la MainView
	public volatile String control;
	
	//Threads
	protected InitThread it;
	protected Thread envoi, recevoir;
	
	//CONSTRUCTEUR
	public Fenetre(){
		
		//Titre de fenêtre
		this.setTitle("RolyPoly SplitFocus Test 0.33");
		
		//Taille de la fenêtre
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		this.setSize(width, height);

		//Plein écran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//Action à la fermeture
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Insertion du panel
		sft = new SplitFocusTestPanel(this.screenSize);
		this.setContentPane(sft);
		
		//Entrées clavier
		pk = new PressKey();
		this.addKeyListener(pk);
		this.addKeyListener(this);
		
		//Entrées souris
		this.addMouseListener(this);
		
		//Entrées touch
		client = new TuioClient();
		client.addTuioListener(this);
		client.connect();
		
		//Définition des booléens
		this.control = "SplitView";
		this.ctrlHere = true;
		this.ctrlThere = false;
		
		//Lancement des threads
		this.it = new InitThread(this);
		this.it.start();
		createThreads();
	}	
	
	//BOUCLE GLOBALE
	public void go(){
		while(true){
			sft.repaint(); //Actualisation du panel
			this.moves = sft.checkIfMoves(); //True si une action est effectuée
		}
	}
	
	//Fonction de mise à jour du controle
	public void setControl(){
		sft.setControl(control);
	}
	
	public void createThreads(){
		//Thread d'envoi
		this.envoi = new SendThread("SplitFocus", this);
		//Thread de réception
		this.recevoir = new ReceiveThread("SplitFocus", this);
	}
	
	//Touche pressée
	public void keyPressed(KeyEvent ke){
		sft.keyPressed(ke);
		if(ke.getKeyCode() == ke.VK_ESCAPE){
			escape();
		}
	}

	//Fonction de fermeture du programme
	public void escape(){
		try{
			this.oos.close();
			this.ois.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		this.alive = false;
		envoi.interrupt();
		recevoir.interrupt();
		it.interrupt();
		System.out.println("Fermeture du programme.");
		Frame[] frames = JFrame.getFrames();
		frames[0].dispatchEvent(new WindowEvent(frames[0], WindowEvent.WINDOW_CLOSING));
	}
	
	public void addTuioCursor(TuioCursor tc) {sft.addTuioCursor(tc);}
	public void removeTuioCursor(TuioCursor tc) {sft.removeTuioCursor(tc);}
	public void mouseClicked(MouseEvent e) {sft.mouseClicked(e);}
	public void mousePressed(MouseEvent e) {sft.mousePressed(e);}
	public void mouseReleased(MouseEvent e) {sft.mouseReleased(e);}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void addTuioBlob(TuioBlob t) {}
	public void addTuioObject(TuioObject t) {}
	public void refresh(TuioTime t) {}
	public void removeTuioBlob(TuioBlob t) {}
	public void removeTuioObject(TuioObject t) {}
	public void updateTuioBlob(TuioBlob t) {}
	public void updateTuioCursor(TuioCursor t) {}
	public void updateTuioObject(TuioObject t) {}

}
