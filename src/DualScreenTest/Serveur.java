package DualScreenTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

	public static void main(String[] args) {  
		
		ServerSocket socket;
		
		try{
			socket = new ServerSocket(4242);
			Thread t = new Thread(new Accepter_clients(socket));
			t.start();
			System.out.println("Serveur prêt.");
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}

class Accepter_clients implements Runnable {
	private ServerSocket socketserver;
	private Socket socket;
	private int nbClient = 1;
	protected int toAdd = 0;
	protected int sum = 0;
	protected BufferedReader in;
	
	public Accepter_clients(ServerSocket s){
		socketserver = s;
	}
	
	public void run(){
		
		try{
			while(true){
				socket = socketserver.accept();
				InetAddress name = socket.getInetAddress();
				System.out.println("Connexion du client n°"+nbClient+" : "+name);
				nbClient++;
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				toAdd = Integer.valueOf(in.read()); 
				sum += toAdd;
				System.out.println(sum-toAdd+" + "+toAdd+" = "+sum);
				socket.close();
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
