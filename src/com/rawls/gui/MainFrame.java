package com.rawls.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame{

	private String title;
	
	public MainFrame(String s)
	{
		title = s;
		
		this.setTitle(title);
		this.setPreferredSize(WindowReference.getDimension());
		this.add(new MainPanel());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
		
		
	}
	
}
