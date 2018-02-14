package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossEnemy extends GameObject {

	private Handler handler;
	private Random r = new Random();
	
	private int movementTimer = 80;
	
	private int shotTimer = 50;
	
	
	public BossEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velX = 0;
		velY = 2;
		
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if(movementTimer <= 0) {
			velY=0;
			shotTimer--;
			
			if(shotTimer <= 0) {
				if(velX == 0) velX = 2;
				
				if(velX > 0) velX += 0.005f;
				else if(velX < 0) velX -= 0.005f;
				
				velX = Game.clamp(velX, -10, 10);
				
				int spawn = r.nextInt(10);
				if(spawn == 0) handler.addObject(new BossEnemyBullet((int)x + 32, (int)y + 32, ID.BasicEnemy, handler));
				
			}
		}
		else movementTimer --;
		
	
		
//		if(y<= 0 || y>= Game.HEIGHT - 90) {
//			velY *= -1;
//		}
//		
		if(x<=0 || x>= Game.WIDTH - 64) {
			velX *= -1;
		}
		
//		handler.addObject(new Trail(x,y, 64,64, ID.Trail, Color.red, 0.008f, handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, 64, 64);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,64,64);
	}
	
	

}
