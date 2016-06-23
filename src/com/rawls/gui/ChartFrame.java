package com.rawls.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ChartFrame extends JFrame {
	
	ChartPanel cp;
	
	public ChartFrame()
	{
		cp = new ChartPanel();
		setTitle("Chart Progress");
		setResizable(false);
		setPreferredSize(new Dimension(1200, 675));
		setLocationRelativeTo(null);
		ChartOptionsFrame cof = new ChartOptionsFrame(cp);
	}

}
