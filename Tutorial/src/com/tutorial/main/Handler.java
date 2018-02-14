package com.tutorial.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	//invokes tick on all our gameObjects
	public void tick() {
		for(int i=0; i<object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	//invokes render on all our gameObjects
	public void render(Graphics g) {
		for(int i=0; i<object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void clearEnemies() {
		for(int i =0; i<object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if(tempObject.getID() != ID.Player) {
				
				removeObject(tempObject);
				i--;
			}
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
}

