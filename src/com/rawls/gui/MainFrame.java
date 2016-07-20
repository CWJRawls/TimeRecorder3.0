package com.rawls.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.rawls.data.Swimmer;

public class MainFrame extends JFrame implements KeyListener{

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
	
	public void switchToTeamPanel()
	{
		mp.switchToTeamView();
	}
	
	public void SwitchToAddSwimmer()
	{
		mp.SwitchToAddSwimmer();
	}
	
	public void switchToSwimmer(Swimmer s)
	{
		mp.switchToSwimmerView(s);
	}
	
	public void switchToChangeDate()
	{
		mp.switchToChangeDate();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		mp.keyTyped(e.getKeyCode());
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		mp.keyPressed(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		mp.keyReleased(e.getKeyCode());
		
	}
	
}
