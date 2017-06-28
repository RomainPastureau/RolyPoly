package MainViewTest;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JFrame;

import Shared.PressKey;
import Shared.Window;
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
	protected MainViewTestPanel sft;
	protected ServerSocket serveurSocket;
	protected Socket clientSocket;
	protected ObjectInputStream ois;
	protected ObjectOutputStream oos;
	protected boolean on, startThreads, menu;
	protected volatile boolean moves;
	protected InitThread it;
	protected Thread envoi, recevoir;
	
	public Fenetre(){
		
		//Titre de fenêtre
		this.setTitle("RolyPoly SplitFocus Test 0.17");
		
		//Taille de la fenêtre
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		this.setSize(width, height);

		//Plein écran
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//Action à la fermeture
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sft = new MainViewTestPanel(this.screenSize);
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
	
	public void updateWindows(ArrayList<Window> windows){
		sft.updateWindows(windows);
	}
	
	public void go(){		
		while(true){
			menu = sft.getMenu();
			if(!menu && startThreads){
				recevoir.start();
				startThreads = false;
			}
			sft.repaint();
			this.moves = this.moves();
		}		
	}
	
	public void initThreads(){		
		this.recevoir = new Thread(new Runnable() {
			ArrayList<Window> windows, tempW;
			boolean alive = true;
			String control = "SplitView";
			boolean ctrl = false;
			@Override
			public void run() {
				windows = sft.getWindows();
				while(alive){
					try {
						ctrl = ois.readBoolean();
						if(ctrl == true){
							control = "SplitView";
						}
						else{
							control = "MainView";
						}
						if(control == "SplitView"){
							tempW = (ArrayList<Window>)ois.readObject();
							if(tempW != null){
								windows = tempW;
								sft.updateWindows(windows);
							}
						}
						alive = ois.readBoolean();
						sft.setAlive(alive);
					} catch (NullPointerException e){
						//System.out.println("Rien n'a été reçu.");
					} catch (SocketException e) {
						System.out.println("Système déconnecté.");
						break;
					} catch (IOException e){
						e.printStackTrace();
					} catch (ClassNotFoundException e){
						e.printStackTrace();
					}
				}
				try{
					System.out.println("Serveur déconnecté");
					ois.close();
					clientSocket.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		});
//		this.envoi = new Thread(new Runnable() {
//			String control = "SplitView";
//			public void run(){
//				while(true){
//					try{
//						if(moves){
//							control = "MainView";
//						}
//						oos.writeUTF(control);
//						oos.flush();
//						oos.reset();
//						if(control == "MainView"){
//							oos.writeObject(sft.getWindows());
//							oos.flush();
//							oos.reset();
//						}
//						oos.writeBoolean(sft.getAlive());
//						oos.flush();
//						oos.reset();
//					} catch(NullPointerException e){
//						System.out.println("Rien n'est envoyé.");
//					} catch(IOException e){
//						e.printStackTrace();
//					}
//				}	
//			}
//		});
	}
	
	public boolean moves(){
		return(sft.moves());
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
