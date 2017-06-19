package DualScreenTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
				case 1: server = "141.115.72.8";
				case 2: server = "141.115.72.18";
			}
			
			socket = new Socket(server, 4242);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(x != -1){
				x = sc.nextInt();
				out.write(x);
			}
			
			socket.close();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
