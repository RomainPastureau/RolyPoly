package MultiTouchTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import Shared.CenterText;
import Shared.Layer;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class TestLayer extends Layer implements KeyListener, MouseListener, TuioListener {
	
	protected HashMap<Integer, TouchPoint> cursors;
	protected boolean stay;
	protected int record;
	protected File f = new File("record.txt");
	protected boolean newRecord;
	
	public TestLayer(Dimension d){
		super(d);
		cursors = new HashMap<Integer, TouchPoint>();
		this.stay = true;
		getRecord();
		this.newRecord = false;
	}
	
	public TestLayer(int x, int y, Dimension d){
		super(x, y, d);
		cursors = new HashMap<Integer, TouchPoint>();
		this.stay = true;
		getRecord();
		this.newRecord = false;
	}
	
	public void paintComponent(Graphics2D g){
		
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Font font = new Font("Calibri", Font.BOLD, 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		Color recordColor = new Color(200, 200, 200);
		if(newRecord){
			recordColor = new Color(255, 0, 0);
		}
		g = CenterText.center((Graphics2D)g, "Record : "+record, font, 80, recordColor, 0, 0, new Dimension((int)width, (int)height));
		int size = cursors.keySet().size();
		for(int i = 0; i < size; i++){
			try{
				if(this.stay || cursors.get(i).isVisible()){
					cursors.get(i).paintComponent((Graphics2D)g);
				}
				if(!this.stay && !cursors.get(i).isVisible()){
					cursors.remove(i);
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
		if(t.getCursorID()+1 > record){
			newRecord = true;
			record = t.getCursorID()+1;
			System.out.println("Nouveau record ! "+record+" points en même temps.");
			saveRecord();
		}
	}
	
	public void removeTuioCursor(TuioCursor t) {
		cursors.get(t.getCursorID()).setVisible(false);
		if(this.stay == false){
			cursors.remove((int)t.getCursorID());
		}
	}

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == ke.VK_ENTER){
			this.stay = !this.stay;
		}
		if(ke.getKeyCode() == ke.VK_NUMPAD0){
			this.record = 0;
			saveRecord();
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
