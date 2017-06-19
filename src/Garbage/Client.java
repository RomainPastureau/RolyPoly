package Garbage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {
		
		Socket socket;
		int x = 0;
		String server = "";
		BufferedWriter out;
		
		try{
			System.out.println("Veuillez sélectionner un serveur :");
			System.out.println("1. 141.115.72.8 (climber)");
			System.out.println("2. 141.115.72.18 (immer)");
			Scanner sc = new Scanner(System.in);
			
			while(x != 1 && x != 2){
				System.out.println("Votre choix : ");
				x = sc.nextInt();
				if(x != 1 && x != 2){
					System.out.println("Erreur, veuillez entrer un nouveau numéro.");
				}
			}
			
			switch(x){
				case 1: server = "141.115.72.8"; break;
				case 2: server = "141.115.72.18"; break;
			}
			
			socket = new Socket(server, 4242);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			AjouterOperation op = new AjouterOperation(out);
			
			Thread t = new Thread(op);
			t.start();
			
			if (op.x == -1){
				socket.close();
			}
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}

class AjouterOperation implements Runnable {
	
	protected int x;
	protected BufferedWriter out;
	protected Scanner sc;
	
	public AjouterOperation(BufferedWriter out){
		x = 0;
		sc = new Scanner(System.in);
	}
	
	public void run(){
		while(x != -1){
			System.out.println("Entrez un nombre.");
			x = sc.nextInt();
			try{
				out.write(x);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
}
