package com.rawls.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame{

	private String title;
	
	private MainPanel mp;
	
	public MainFrame(String s)
	{
		title = s;
		mp = new MainPanel();
		this.setTitle(title);
		this.setPreferredSize(WindowReference.getDimension());
		this.add(mp);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	
	}
	
	public void changeToDatePanel()
	{
		mp.switchToDateView();
	}
	
}
