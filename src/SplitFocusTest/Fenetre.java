package SplitFocusTest;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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
	
	private static final long serialVersionUID = 1L;
	protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	protected int width, height;
	protected PressKey pk;
	protected TuioClient client;
	protected SplitFocusTestPanel sft;
	protected ServerSocket serveurSocket;
	protected Socket clientSocket;
	protected ObjectOutputStream oos;
	protected boolean on, startThreads, menu;
	protected InitThread it;
	protected Thread envoi;
	
	public Fenetre(){
		
		//Titre de fenêtre
		this.setTitle("RolyPoly SplitFocus Test 0.2");
		
		//Taille de la fenêtre
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		this.setSize(width, height);

		//Plein écran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//Action à la fermeture
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		this.on = false;
		this.startThreads = true;
		
		this.it = new InitThread(this);
		this.it.start();
		initThreads();
	}	
	
	public void go(){
		while(true){
			menu = sft.getMenu();
			if(!menu && startThreads){
				envoi.start();
				startThreads = false;
			}
			sft.repaint();
		}
	}
	
	public void initThreads(){
		this.envoi = new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						oos.writeObject(sft.getWindows());
						oos.flush();
						oos.reset();
						oos.writeObject(sft.getImages());
						oos.flush();
						oos.reset();
					} catch(NullPointerException e){
						System.out.println("Rien n'est envoyé.");
					} catch(IOException e){
						e.printStackTrace();
					}
				}	
			}
		});
	}
	
	public void addTuioCursor(TuioCursor tc) {
		sft.addTuioCursor(tc);
	}
	
	public void removeTuioCursor(TuioCursor tc) {
		sft.removeTuioCursor(tc);
	}

	public void mouseClicked(MouseEvent e) {
		sft.mouseClicked(e);		
	}
	
	public void keyPressed(KeyEvent ke) {
		sft.keyPressed(ke);
	}
	
	public void mousePressed(MouseEvent e) {
		sft.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		sft.mouseReleased(e);
	}
	
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
