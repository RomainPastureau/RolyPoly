package DualScreenTest;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import java.io.IOException;

public class Client {
	
	public static void main(String[] args) {
		
		Socket socket;
		
		try{
			socket = new Socket(InetAddress.getLocalHost(), 4242);
			socket.close();
		} catch(UnknownHostException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
