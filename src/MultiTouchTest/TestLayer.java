package MultiTouchTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import Shared.CenterText;
import Shared.Layer;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import java.io.IOException;

public class TestLayer extends Layer implements KeyListener, MouseListener, TuioListener {
	
	protected HashMap<Integer, TouchPoint> cursors;
	protected boolean stay;
	protected static int record;
	protected File f = new File("record.txt");
	
	public TestLayer(Dimension d){
		super(d);
		cursors = new HashMap<Integer, TouchPoint>();
		this.stay = true;
		getRecord();
	}
	
	public TestLayer(int x, int y, Dimension d){
		super(x, y, d);
		cursors = new HashMap<Integer, TouchPoint>();
		this.stay = true;
		getRecord();
	}
	
	public void paintComponent(Graphics g){
		Font font = new Font("Calibri", Font.BOLD, 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		g = CenterText.center((Graphics2D)g, "Record : "+record, font, 80, new Color(200, 200, 200), 0, 0, new Dimension((int)width, (int)height));
		for(int i = 0; i < cursors.keySet().size(); i++){
			try{
				if(this.stay || cursors.get(i).isVisible()){
					cursors.get(i).paintComponent((Graphics2D)g);
				}
			} catch(NullPointerException e){
				break;
			}
		}
	}
	
	public void saveRecord(){
		DataOutputStream dos;
		try{
			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));	
			dos.writeInt(record);
			dos.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void getRecord(){
		DataInputStream dis;
		try{
			dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
			record = dis.readInt();
			dis.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void addTuioCursor(TuioCursor t) {
		cursors.put(t.getCursorID(), new TouchPoint(t));
		if(t.getCursorID() > record){
			record = t.getCursorID();
			saveRecord();
		}
	}
	
	public void removeTuioCursor(TuioCursor t) {
		cursors.get(t.getCursorID()).setVisible(false);
		if(this.stay == false){
			cursors.remove(t.getCursorID());
		}
	}

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == ke.VK_ENTER){
			this.stay = !this.stay;
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	public void addTuioBlob(TuioBlob t) {}
	public void addTuioObject(TuioObject t) {}
	public void refresh(TuioTime t) {}
	public void removeTuioBlob(TuioBlob t) {}
	public void removeTuioObject(TuioObject t) {}
	public void updateTuioBlob(TuioBlob t) {}
	public void updateTuioCursor(TuioCursor t) {}
	public void updateTuioObject(TuioObject t) {}
	
}
