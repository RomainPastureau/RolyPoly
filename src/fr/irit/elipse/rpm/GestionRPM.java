package fr.irit.elipse.rpm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GestionRPM implements Runnable
{
	private static final int PORT = 8081;
	ServerSocket server;
	SocketIMU imu;
	
	private ArrayList<RPMListener> rpmListeners;
	
	public GestionRPM()
	{
		rpmListeners = new ArrayList<RPMListener>();
	}
	
	public void close()
	{
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addRPMListener(RPMListener rpm)
	{
		while(rpmListeners.size()>0)
			rpmListeners.remove(0);
		rpmListeners.add(rpm);
	}
	
	public void sendMovement(double angle, double inclinaison)
	{
		for(int i=0;i<rpmListeners.size();i++)
			rpmListeners.get(i).rpmMoved(angle, inclinaison);
	}
	
	public void sendTouch() {
			for(int i=0;i<rpmListeners.size();i++)
				rpmListeners.get(i).rpmTouch();
	}
	
	public void run() 
	{
			try {
				server = new ServerSocket(PORT);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(true)
			{
				try {
					Socket client = server.accept();
					imu = new SocketIMU(client,this);
					Thread t = new Thread(imu);
					t.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
}
