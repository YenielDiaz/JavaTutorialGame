package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

	Handler handler;
	
	Random r = new Random();
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
//		velX = r.nextInt(5) + 1;
//		velY = r.nextInt(5) + 1;
		
	}

	@Override
	public void tick() {
		//this moves the object according to its speed;
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 40);
		y = Game.clamp(y, 0, Game.HEIGHT - 64);
		
		collision();
	}
	
	private void collision() {
		for(int i=0; i<handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy) { //tempObject is now BasicEnemy
				if(getBounds().intersects(tempObject.getBounds())) {
					//collision code
					HUD.HEALTH -= 2;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		//set player color and dimensions
		g.setColor(Color.white);
		g.fillRect(x, y, 32, 32);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}

	
}
