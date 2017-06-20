package DualScreenTest;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
	protected DualScreenTestPanel dst;
	protected TuioClient client;
	protected String type;
	protected ServerSocket serveurSocket;
	protected Socket clientSocket;
	protected PrintWriter out;
	protected BufferedReader in;
	protected Thread envoi, recevoir;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	
	public Fenetre(){
		
		//Titre de fenêtre
		this.setTitle("RolyPoly DualScreen Test 0.8");
		
		this.type = "Client";
		
		if(this.type.equals("Serveur")){
			//Taille de la fenêtre
			width = (int)screenSize.getWidth();
			height = (int)screenSize.getHeight();
			this.setSize(width, height);
	
			//Plein écran
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
			
			//Action à la fermeture
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			dst = new DualScreenTestPanel(this.screenSize, this.type);
			this.setContentPane(dst);
		}
		
		if(this.type.equals("Serveur")){
			try{
				serveurSocket = new ServerSocket(4242);
				clientSocket = serveurSocket.accept();
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			} catch(IOException e){
				e.printStackTrace();
			}
			this.envoi = new Thread(new Runnable() {
				public void run() {
					long time = System.currentTimeMillis();
					while(true){
						if(System.currentTimeMillis() > time+1000){
							try{
								oos.writeObject(dst.getCar());
								oos.flush();
							} catch(IOException e){
								e.printStackTrace();
							}
							time = System.currentTimeMillis();
						}
					}	
				}
			});
		}
		
		else{
			try{
				clientSocket = new Socket("141.115.72.18", 4242);
				ois = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			} catch(IOException e){
				e.printStackTrace();
			}
			this.recevoir = new Thread(new Runnable() {
				Coordinates c;
				@Override
				public void run() {
					try {						
						c = (Coordinates)ois.readObject();
						while(c!=null){
							System.out.println(c);
							c = (Coordinates)ois.readObject();
							dst.updateCoordinates(c);
						}
						System.out.println("Serveur déconecté");
						ois.close();
						clientSocket.close();
					} catch (NullPointerException e){
						System.out.println("Rien n'a été reçu.");
					} catch (SocketException e) {
						System.out.println("Système déconnecté.");
					} catch (IOException e){
						e.printStackTrace();
					} catch (ClassNotFoundException e){
						e.printStackTrace();
					}
				}
			});
		} 
		
		//Entrées clavier
		pk = new PressKey();
		this.addKeyListener(pk);
		this.addMouseListener(this);
		this.addKeyListener(this);
		client = new TuioClient();
		client.addTuioListener(this);
		client.connect();		
	}	
	
	public void go(){
		if(this.type.equals("Serveur")){
			envoi.start();
		}
		else{
			recevoir.start();
		}
		while(true){
			dst.repaint();
		}
	}
	
	public void addTuioCursor(TuioCursor tc) {
		dst.addTuioCursor(tc);
	}
	
	public void removeTuioCursor(TuioCursor tc) {
		dst.removeTuioCursor(tc);
	}

	public void mouseClicked(MouseEvent e) {
		dst.mouseClicked(e);		
	}
	
	public void keyPressed(KeyEvent ke) {
		dst.keyPressed(ke);
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
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
