package DualScreenTest;

import java.io.IOException;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) {
		
		Socket socket;
		
		try{
			socket = new Socket("localhost", 4242);
			socket.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
