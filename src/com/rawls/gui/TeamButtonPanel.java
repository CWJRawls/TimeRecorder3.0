package com.rawls.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.rawls.main.RecorderMain;

public class TeamButtonPanel extends JPanel implements ActionListener{

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
			buttons[i].addActionListener(this);
			this.add(buttons[i]);
		}
		
		//Set Buttons to unimplemented functions as off
		buttons[6].setEnabled(false);
		buttons[7].setEnabled(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int but = -1;
		
		for(but = 0; but < buttons.length && !e.getSource().equals(buttons[but]); but++){}
		
		switch(but)
		{
		case 0:
			RecorderMain.SwitchToAddSwimmer();
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		}

		
	}
}
