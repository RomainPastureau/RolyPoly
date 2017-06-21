package fr.irit.elipse.rpm;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.Timer;

public class SocketIMU implements Runnable
{
	private float _xImuValue = 0;
	private float _yImuValue = 0;

	private static float _anglePolaire = 0;
	private float _longueurPolaire = 0;
	
	public GestionRPM rpm;
	Socket client;
	Timer timer;

	public SocketIMU(Socket client, GestionRPM rpm)
	{
		this.client = client;
		this.rpm = rpm;
		timer = new Timer(15, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rpm.sendMovement(_anglePolaire, _longueurPolaire);
			}
		});
		timer.start();
	}
	
	public void run() 
	{
		try {
			BufferedReader plec = new BufferedReader(new InputStreamReader(client.getInputStream()));
			boolean socketOuvert = true;
			while (socketOuvert) {
				setStringImu(plec.readLine());
			}
			plec.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStringImu(String stringImu) {
		String[] valeurs = stringImu.split(":");
		_xImuValue = Float.parseFloat(valeurs[1].replaceAll(",", "."));
		_yImuValue = Float.parseFloat(valeurs[2].replaceAll(",", "."));
		_anglePolaire = (float) (Math.toDegrees(Math.atan2(_yImuValue, _xImuValue))-90);
		_longueurPolaire = (float) Math.sqrt((_xImuValue*_xImuValue)+(_yImuValue*_yImuValue));
	}
}
