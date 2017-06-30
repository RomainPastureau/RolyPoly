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
//				ctrlThere = ois.readBoolean();
//				if(!moves && ctrlThere){
//					control = "MainView";
//					setControl();
//				}
				fenetre.ctrlThere = fenetre.ois.readBoolean();
				if(fenetre.ctrlThere){
					tempW = (ArrayList<Window>)fenetre.ois.readObject();
					fenetre.sft.setControl(fenetre.control);
					if(tempW != null){
						windows = tempW;
						fenetre.sft.updateWindows(windows);
					}
				}
				fenetre.sft.setAlive(fenetre.alive);
			} catch (NullPointerException e){
				//System.out.println("Rien n'a été reçu.");
			} catch (SocketException e) {
				System.out.println("Système déconnecté.");
			} catch (IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		try{
			System.out.println("Serveur déconnecté");
			fenetre.ois.close();
			fenetre.clientSocket.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
