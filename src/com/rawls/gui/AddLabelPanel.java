package com.rawls.gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AddLabelPanel extends JPanel {
	
	private String labText = "Adding A Swimmer";
	private JLabel label;

	public AddLabelPanel()
	{
		/*------ Setup the Window -------*/
		/*-------------------------------*/
		this.setLayout(null);
		Dimension d = WindowReference.getDimension();
		Dimension curr = new Dimension(d.width / 2, d.height);
		this.setPreferredSize(curr);
		
		/*----- Create and Add JLabel -----*/
		/*---------------------------------*/
		
		label = new JLabel(labText);
		
		Font lFont = new Font(label.getFont().getName(),Font.BOLD, label.getFont().getSize());
		
		int cWidth = (int)(curr.width * 0.8);
		
		while(label.getFontMetrics(lFont).stringWidth(labText) < (cWidth * 0.9))
		{
			lFont = new Font(lFont.getName(), Font.BOLD, lFont.getSize() + 1);
		}
		
		label.setFont(lFont);
		
		int lHeight = label.getFontMetrics(lFont).getHeight();
		int topSpace = (int)((curr.height * 0.4) - (lHeight / 2.0));
		int leftSpace = (int)(curr.width * 0.1);
		
		label.setBounds(leftSpace, topSpace, cWidth, lHeight);
		
		this.add(label);
	}
	
}
