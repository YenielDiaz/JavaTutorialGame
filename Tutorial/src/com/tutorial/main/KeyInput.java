package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private boolean[] keyDown = new boolean[4];
	
	private Handler handler;
	
	
	public KeyInput(Handler handler) {
		this.handler  = handler;
		
		keyDown[0] = false; // W
		keyDown[1] = false; // S
		keyDown[2] = false; // A
		keyDown[3] = false; // D
	}
	public void keyPressed(KeyEvent e) {
		int key  = e.getKeyCode();
		
		for(int i = 0; i<handler.object.size(); i++ ) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getID().equals(ID.Player)) {
				//key events for player object
				if(key == KeyEvent.VK_W) {
					tempObject.setVelY(-5);
					keyDown[0] = true;
				}
				if(key == KeyEvent.VK_A) {
					tempObject.setVelX(-5);
					keyDown[2] = true;
				}
				if(key == KeyEvent.VK_S) {
					tempObject.setVelY(5);
					keyDown[1] = true;
				}
				if(key == KeyEvent.VK_D) {
					tempObject.setVelX(5);
					keyDown[3] = true;
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key  = e.getKeyCode();
		
		for(int i = 0; i<handler.object.size(); i++ ) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getID().equals(ID.Player)) {
				//key events for player object
				if(key == KeyEvent.VK_W) {
					keyDown[0] = false;
					//tempObject.setVelY(0);
				}
				if(key == KeyEvent.VK_A) {
					keyDown[2] = false;
					//tempObject.setVelX(0);
				}
				if(key == KeyEvent.VK_S) {
					keyDown[1] = false;
					//tempObject.setVelY(0);
				}
				if(key == KeyEvent.VK_D) {
					keyDown[3] = false;
					//tempObject.setVelX(0);
					
				}
				
				//vertical movement
				if(!keyDown[0] && !keyDown[1]) {
					tempObject.setVelY(0);
				}
				
				//horizontal movement
				if(!keyDown[2] && !keyDown[3]) {
					tempObject.setVelX(0);
				}
			}
		}
		//closes game when esc key is pressed
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
}
