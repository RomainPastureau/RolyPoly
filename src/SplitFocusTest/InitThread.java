package SplitFocusTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SingleSelectionModel;

public class InitThread extends Thread {

	protected String type;
	protected ServerSocket serveurSocket;
	protected Socket clientSocket;
	protected Fenetre fenetre;
	protected volatile ObjectOutputStream oos;
	protected volatile ObjectInputStream ois;
	
	public InitThread(Fenetre fen){
		this.fenetre = fen;
	}
	
	@Override
	public void run() {
		if(!fenetre.on){
			try{
				serveurSocket = new ServerSocket(4242);
				fenetre.clientSocket = serveurSocket.accept();
				LaunchMyServer();

			} catch(IOException e){
				e.printStackTrace();
			}

		}
		this.interrupt();
	}
	
	
	public void LaunchMyServer(){
		
		Thread ServerThread = new Thread(new Runnable() {
			public void run(){
				
				try {
					
					fenetre.oos = new ObjectOutputStream(fenetre.clientSocket.getOutputStream());
					System.out.println("Connexion sortante OK");

					fenetre.ois = new ObjectInputStream(fenetre.clientSocket.getInputStream());
					System.out.println("Connexion entrante OK");
					
					// Affichage seulement
					fenetre.sft.setConnect(true);
					// lancer la communication
					fenetre.on = true;
					fenetre.envoi.start();
					fenetre.recevoir.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		ServerThread.start();
	}

	
	public boolean SendData(String data){
		
		
		
		return true;
	}
	
	
	
}
