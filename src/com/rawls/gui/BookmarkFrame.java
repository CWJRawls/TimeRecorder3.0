package com.rawls.gui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class BookmarkFrame extends JFrame {

	private BookmarkPanel bp;
	
	public BookmarkFrame()
	{
		//get screen size and set this window to take 80% in each dimension
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = 1000;//(int)(gd.getDisplayMode().getWidth() * 0.8);
		int height = 800;//(int)(gd.getDisplayMode().getHeight() * 0.8);	
		//finish setting up the window
		this.setPreferredSize(new Dimension(width, height));
		this.setTitle("Create Bookmarks");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		bp = new BookmarkPanel(width, height);
		this.add(bp);
		this.pack();
		this.setVisible(true);
	}
	
}
