package SplitFocusTest;

import java.io.IOException;
import java.net.SocketException;

public class SendThread extends Thread {
	
	protected String type;
	protected Fenetre fenetre;
	
	public SendThread(String type, Fenetre fenetre){
		this.type = type;
		this.fenetre = fenetre;
	}
	
	public void run(){
		while(true){
			try{
				if(fenetre.moves){
					fenetre.control = "SplitView";
					fenetre.ctrlHere = true;
				}
				else{
					fenetre.ctrlHere = false;
				}
				fenetre.oos.writeBoolean(fenetre.moves);
				fenetre.oos.flush();
				fenetre.oos.reset();
				if(fenetre.moves){
					fenetre.oos.writeObject(fenetre.sft.getWindows());
					fenetre.oos.flush();
					fenetre.oos.reset();
				}
				fenetre.oos.writeBoolean(fenetre.sft.getAlive());
				fenetre.oos.flush();
				fenetre.oos.reset();
			} catch(NullPointerException e){
				//System.out.println("Rien n'est envoyé.");
			} catch(SocketException e){
				System.out.println("Déconnexion");
				break;
			} catch(IOException e){
				e.printStackTrace();
			}
		}	
	}
}
