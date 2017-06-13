package Shared;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class PressKey implements KeyListener {
	
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode() == ke.VK_ESCAPE){
			escape();
		}
	}
	
	public void keyReleased(KeyEvent ke){
	}
	
	public void keyTyped(KeyEvent ke){
	}
	
	//Fonction de fermeture du programme
	public void escape(){
		System.out.println("Fermeture du programme.");
		Frame[] frames = JFrame.getFrames();
		frames[0].dispatchEvent(new WindowEvent(frames[0], WindowEvent.WINDOW_CLOSING));
	}
	
}
