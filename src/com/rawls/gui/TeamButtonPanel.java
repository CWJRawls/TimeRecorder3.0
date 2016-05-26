package com.rawls.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TeamButtonPanel extends JPanel {

	private JButton[] buttons = new JButton[9];
	private String[] butLabels = {"Add Swimmer", "Edit Swimmer", "RemoveSwimmer", "Save", "Save As", "Create Timesheet", "Chart Performance (Upcoming)", "Create Bookmarks (Upcoming)", "Exit"};
	
	private TeamListPanel tlp;
	
	public TeamButtonPanel(TeamListPanel tlp)
	{
		//set reference to a team list panel
		this.tlp = tlp;
		
		//get the current dimension
		Dimension d = WindowReference.getDimension();
		Dimension curr = new Dimension(d.width / 2, d.height);
		
		//set general panel properties
		this.setLayout(null);
		this.setPreferredSize(curr);
		
		int bWidth = (int)(curr.width * 0.75);
		int bHeight = (int)(curr.height * 0.07);
		int bSpace = (int)(curr.height * 0.03);
		int leftSpace = (int)((curr.width * 0.25) / 2.0);
		int topSpace = (int)((curr.height * 0.1) / 2.0);
		
		for(int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton(butLabels[i]);
			buttons[i].setBounds(leftSpace, topSpace + (i * bSpace) + (i * bHeight), bWidth, bHeight);
			this.add(buttons[i]);
		}
		
		//Set Buttons to unimplemented functions as off
		buttons[6].setEnabled(false);
		buttons[7].setEnabled(false);
		
	}
}
