package MainViewTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class InitThread extends Thread {

	protected String type;
	protected ServerSocket serveurSocket;
	protected Socket clientSocket;
	protected Fenetre fenetre;
	
	public InitThread(Fenetre fen){
		this.fenetre = fen;
	}
	
	@Override
	public void run() {
		if(!fenetre.on){
			try{
				fenetre.clientSocket = new Socket("141.115.72.18", 4242);
				fenetre.oos = new ObjectOutputStream(new BufferedOutputStream(fenetre.clientSocket.getOutputStream()));
				System.out.println("Connexion sortante OK");
				fenetre.ois = new ObjectInputStream(new BufferedInputStream(fenetre.clientSocket.getInputStream()));
				System.out.println("Connexion entrante OK");
				fenetre.sft.setConnect(true);
				fenetre.on = true;
			} catch(ConnectException e){
				System.out.println("Connexion refusée.");
			} catch(IOException e){
				e.printStackTrace();
			} 
		} 	
		this.interrupt();
	}

}
