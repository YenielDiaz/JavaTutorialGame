package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = -240840600533728354L;

	
	public Window(int width, int height, String title, Game game) {
		JFrame screen = new JFrame(title);
		
		//find out why this is needed
		screen.setPreferredSize(new Dimension(width,height));
		screen.setMaximumSize(new Dimension(width,height));
		screen.setMinimumSize(new Dimension(width,height));
		
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setResizable(false);
		screen.setLocationRelativeTo(null);
		screen.add(game);
		screen.setVisible(true);
		screen.setFocusable(true);
		
		
		game.start();
	}
}
