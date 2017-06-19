package DualScreenTest;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	public Fenetre(){
		
		InetAddress adresseLocale;
		InetAddress adresseServeur;
		
		try{
			adresseLocale = InetAddress.getLocalHost();
			System.out.println("L'adresse locale est : "+adresseLocale);
			adresseServeur = InetAddress.getByName("www.google.fr");
			System.out.println("L'adresse du serveur de Google est : "+adresseServeur);
			adresseServeur = InetAddress.getByName("www.facebook.fr");
			System.out.println("L'adresse du serveur de Facebook est : "+adresseServeur);
			ServerSocket socketserver = new ServerSocket(0, 2);
			System.out.println(socketserver);
			Socket socket = new Socket("141.115.72.18", socketserver.getLocalPort());
			Socket socketduserveur = socketserver.accept();
			System.out.println(socketduserveur);
			
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
//		System.out.println(gds[0].getDisplayModes());
//		for (GraphicsDevice gd : localGE.getScreenDevices()) {
//		  for (GraphicsConfiguration graphicsConfiguration : gd.getConfigurations()) {
//			System.out.println(graphicsConfiguration.getBounds());
//		    result.union(result, graphicsConfiguration.getBounds(), result);
//		  }
//		}
//		System.out.println("RESULT: "+result);
		//f.setSize(result.getWidth(), result.getHeight());
//		//Titre de fenêtre
//		this.setTitle("RolyPoly MultiTouch Test 1.6");
//		
//		//Taille de la fenêtre
//		width = (int)screenSize.getWidth();
//		height = (int)screenSize.getHeight();
//		this.setSize(width, height);
//
//		//Plein écran
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		this.setUndecorated(true);
//		
//		//Action à la fermeture
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	
//		dst = new DualScreenTestPanel(this.screenSize);
//		this.setContentPane(dst);
//		
//		//Entrées clavier
//		pk = new PressKey();
//		this.addKeyListener(pk);
//		this.addMouseListener(this);
//		this.addKeyListener(this);
//		client = new TuioClient();
//		client.addTuioListener(this);
//		client.connect();
	}	
	
	public void go(){
//		while(true){
//			dst.repaint();
//		}
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
