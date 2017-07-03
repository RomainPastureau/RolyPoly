package SplitFocusTest;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import Shared.Window;

public class ReceiveThread extends Thread {
	
	protected ArrayList<Window> windows, tempW;
	protected String type;
	protected Fenetre fenetre;
	
	public ReceiveThread(String type, Fenetre fenetre){
		this.type = type;
		this.fenetre = fenetre;
	}
	
	public void run() {
		windows = fenetre.sft.getWindows();
		while(fenetre.alive){
			try {
				//On lit le bool�en.
				fenetre.ctrlThere = fenetre.ois.readBoolean();
				if(!fenetre.moves && fenetre.ctrlThere){
					fenetre.control = "MainView";
					fenetre.setControl();
				}
				//S'il est � true, on lit la liste de fen�tres
				if(fenetre.ctrlThere){
					tempW = (ArrayList<Window>)fenetre.ois.readObject();
					if(tempW != null){
						windows = tempW;
						fenetre.sft.setWindows(windows);
					}
				}
				//Enfin, on v�rifie que le programme distant soit vivant.
				fenetre.sft.setAlive(fenetre.alive);
			} catch (NullPointerException e){
				System.out.println("Rien n'a �t� re�u.");
			} catch (SocketException e) {
				System.out.println("Syst�me d�connect�.");
			} catch (IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		try{
			System.out.println("Serveur d�connect�");
			fenetre.ois.close();
			fenetre.clientSocket.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
