package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject {

	private Handler handler;
	private GameObject player;
	
	
	public SmartEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		//this loop allows us to use player object and his qualities in this class. i think
		for(int i=0; i<handler.object.size(); i++) {
			if(handler.object.get(i).getID() == ID.Player) player = handler.object.get(i);
		}

		
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		
		//"distance algorithm"
		float distance = (float) Math.sqrt((x - player.getX()) * (x - player.getX()) + (y-player.getY()) * (y - player.getY()));
		
		velX = (float) ((-1.0/distance)*diffX);
		velY = (float) ((-1.0/distance)*diffY);
		//end of "distance algorithm"
		
		if(y<= 0 || y>= Game.HEIGHT - 32) {
			velY *= -1;
		}
		
		if(x<=0 || x>= Game.WIDTH - 16) {
			velX *= -1;
		}
		
		handler.addObject(new Trail(x,y, 16,16, ID.Trail, Color.yellow, 0.02f, handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,16,16);
	}
	
	

}
