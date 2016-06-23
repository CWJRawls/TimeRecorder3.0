package com.rawls.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ChartOptionsFrame extends JFrame{

	private ChartPanel cp;
	private ChartOptionsPanel cop;
	
	public ChartOptionsFrame(ChartPanel cp)
	{
		this.cp = cp;
		cop = new ChartOptionsPanel();
		this.setPreferredSize(new Dimension(500, 525));
		this.setTitle("Chart Options");
		this.add(cop);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
}
