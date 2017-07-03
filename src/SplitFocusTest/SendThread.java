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
				//Si l'utilisateur effectue une action, on passe ctrlHere à true
				if(fenetre.moves){
					fenetre.ctrlHere = true;
					fenetre.control = "SplitFocus";
					fenetre.setControl();
				}
				else{
					fenetre.ctrlHere = false;
				}
				//On envoie d'abord si l'utilisateur effectue une action
				fenetre.oos.writeBoolean(fenetre.moves);
				fenetre.oos.flush();
				fenetre.oos.reset();
				//Si c'est le cas, on ajoute l'arraylist des fenêtres
				if(fenetre.moves){
					fenetre.oos.writeObject(fenetre.sft.getWindows());
					fenetre.oos.flush();
					fenetre.oos.reset();
				}
				//Enfin, on envoie si le programme est vivant ou non
				fenetre.oos.writeBoolean(fenetre.sft.getAlive());
				fenetre.oos.flush();
				fenetre.oos.reset();
			} catch(NullPointerException e){
				System.out.println("Rien n'est envoyé.");
			} catch(SocketException e){
				System.out.println("Déconnexion");
				break;
			} catch(IOException e){
				e.printStackTrace();
			}
		}	
	}
}
