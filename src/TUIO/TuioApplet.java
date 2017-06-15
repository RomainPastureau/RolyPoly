package TUIO;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JApplet;

public class TuioApplet extends JApplet {
	
	private static final long serialVersionUID = 1L;
	TuioDemoComponent demo;
	TuioClient client;
	int port = 3333;
	
	public void init() {
		try {
			port = Integer.parseInt(getParameter("port"));
		} catch (Exception e) {}
		
		Dimension size = this.getSize();
		
		TuioDemoComponent demo = new TuioDemoComponent();
		demo.setSize(size.width,size.height);
	
		client = new TuioClient();
		client.addTuioListener(demo);
		
		add(demo);
		repaint();
	}

	public void start() {
		if (!client.isConnected()) client.connect();
	}

	public void stop() {
		if (client.isConnected()) client.disconnect();
	}

	public void destroy() {
		if (client.isConnected()) client.disconnect();
		client = null;
	}

	public void paint( Graphics g ) {
	}
}
