package SplitFocusTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
		try{
			serveurSocket = new ServerSocket(4242);
			fenetre.clientSocket = serveurSocket.accept();
			LaunchMyServer();

		} catch(IOException e){
			e.printStackTrace();
		}
		
		this.interrupt();
	}
	
	
	public void LaunchMyServer(){
		Thread ServerThread = new Thread(new Runnable() {
			public void run(){
				
				try {
					//Lancement de l'�criture
					fenetre.oos = new ObjectOutputStream(fenetre.clientSocket.getOutputStream());
					System.out.println("Connexion sortante OK");

					//Lancement de la lecture
					fenetre.ois = new ObjectInputStream(fenetre.clientSocket.getInputStream());
					System.out.println("Connexion entrante OK");
					
					//Gestion de l'affichage
					fenetre.sft.setConnect(true);
					
					//Lancement de la communication
					fenetre.envoi.start();
					fenetre.recevoir.start();
				} catch (IOException e) {
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
