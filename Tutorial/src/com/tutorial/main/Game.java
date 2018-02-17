package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
 
	
	private static final long serialVersionUID = -7467682547794408986L;
	
	public static final int WIDTH = 640;
	
	//tutorial guy made height this because it creates a sort of ratio that he prefers.
	public static final int HEIGHT = WIDTH/12 * 9;
	
	private Thread thread;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Random r;
	private boolean running = false;
	private Menu menu;
	
	public enum STATE{
		Menu,
		Help,
		Game,
		GameOver;
	};
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		
		handler = new Handler();
		hud  = new HUD();
		
		menu = new Menu(this, handler, hud);
		
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop();
		
		new Window(WIDTH, HEIGHT, "Tutorial Game", this);
		
		
		spawner = new Spawn(handler, hud);
		
		r = new Random();
		
		//make 50 players at different positions
		//for(int i = 0; i<50; i++) {
		//	handler.addObject(new Player(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.Player));
			// this starts them all off at same position.
			//handler.addObject(new Player(0,0, ID.Player));
		//}
		
		if(gameState == STATE.Game) {
			//adds player object to game
			handler.addObject(new Player(WIDTH/2 - 32,HEIGHT/2 - 32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
		}else {
			for(int i = 0; i<20; i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.MenuParticle, handler));
			}
		}
		
		
		//handler.addObject(new BossEnemy((Game.WIDTH / 2) - 48, -74, ID.BossEnemy, handler));
	}

	public static void main(String[] args) {
		new Game();
	}

	@Override
	//find out how this works. tutorial guy could not quite explain.
	public void run() {
		//gameLoop to make game keep updating itself
		//tutorial guy says it is not his own code
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer+= 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
		
	}
	
	private void tick() {
		handler.tick();
		
		if(gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			if(hud.HEALTH ==0) {
				hud.HEALTH = 100;
				gameState = STATE.GameOver;
				handler.clearEnemies();
				handler.object.clear();
				for(int i = 0; i<20; i++) {
					handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.MenuParticle, handler));
				}
				
			}
		}else if(gameState == STATE.Menu || gameState == STATE.GameOver) {
			menu.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			//creates 3 buffers within game
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(gameState == STATE.Game) {
			
			hud.render(g);
		}else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.GameOver) {
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max) {
			return var =  max;
		}else if(var <= min) {
			return var =  min;
		}else {
			return var;
		}
	}
	
	public synchronized void start() {
		//initializes thread (this refers to this game instance) and starts it
		thread = new Thread(this);
		thread.start();
		this.requestFocus();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			//thread.join basically just stops the thread
			thread.join();
			running = false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
