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
	
	//G�n�ral
	protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Taille de la fen�tre
	protected int width, height; //Taille de la fen�tre
	protected SplitFocusTestPanel sft; //Panel JFrame
	
	//Entr�es souris/tactiles/clavier
	protected PressKey pk; //Permet de fermer la fen�tre
	protected TuioClient client; //Permet de r�cup�rer le multitouch
	
	//Communication
	protected volatile ServerSocket serveurSocket; 
	protected volatile Socket clientSocket;
	protected volatile ObjectOutputStream oos;
	protected volatile ObjectInputStream ois;
	
	//Bool�ens et String divers
	protected volatile boolean moves; //True si une action est effectu�e sur la fen�tre en cours, false sinon
	protected volatile boolean alive; //True si le programme n'a pas �t� ferm�
	protected volatile boolean ctrlHere, ctrlThere; //True si un controle est effectu� sur la SplitView/la MainView
	public volatile String control;
	
	//Threads
	protected InitThread it;
	protected Thread envoi, recevoir;
	
	//CONSTRUCTEUR
	public Fenetre(){
		
		//Titre de fen�tre
		this.setTitle("RolyPoly SplitFocus Test 0.33");
		
		//Taille de la fen�tre
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		this.setSize(width, height);

		//Plein �cran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//Action � la fermeture
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Insertion du panel
		sft = new SplitFocusTestPanel(this.screenSize);
		this.setContentPane(sft);
		
		//Entr�es clavier
		pk = new PressKey();
		this.addKeyListener(pk);
		this.addKeyListener(this);
		
		//Entr�es souris
		this.addMouseListener(this);
		
		//Entr�es touch
		client = new TuioClient();
		client.addTuioListener(this);
		client.connect();
		
		//D�finition des bool�ens
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
			this.moves = sft.checkIfMoves(); //True si une action est effectu�e
		}
	}
	
	//Fonction de mise � jour du controle
	public void setControl(){
		sft.setControl(control);
	}
	
	public void createThreads(){
		//Thread d'envoi
		this.envoi = new SendThread("SplitFocus", this);
		//Thread de r�ception
		this.recevoir = new ReceiveThread("SplitFocus", this);
	}
	
	//Touche press�e
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
