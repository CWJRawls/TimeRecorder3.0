package com.rawls.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.rawls.data.Swimmer;
import com.rawls.main.RecorderMain;
import com.rawls.storage.SwimmerMasterList;

public class TeamButtonPanel extends JPanel implements ActionListener{

	private JButton[] buttons = new JButton[11];
	private String[] butLabels = {"Add Swimmer", "Edit Swimmer", "Find Swimmer By Number", "Find Swimmer By Name", "Remove Swimmer", "Save", "Save As", "Create Timesheet", "Chart Performance (Upcoming)", "Create Bookmarks (Upcoming)", "Exit"};
	
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
		int bSpace = (int)(curr.height * 0.01);
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
		buttons[8].setEnabled(false);
		buttons[9].setEnabled(false);
		
	}
	
	private void attemptToRemoveSwimmer()
	{
		int select = tlp.getSelectedIndex();
		Swimmer s = SwimmerMasterList.getSwimmer(select);
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove " + s.getFormattedName() + "?");
		
		if(result == JOptionPane.YES_OPTION)
		{
			SwimmerMasterList.removeSwimmer(select);
			tlp.updateList();
		}
	}
	
	private void attemptClose()
	{
		int result = JOptionPane.showOptionDialog(this, "Are you sure you want to exit.\nAll unsaved data will be lost!", "Exit?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] {"Don't Save", "Save", "Cancel"}, 1);
		if(result == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
		else if(result == JOptionPane.NO_OPTION)
		{
			//Attempt to save
			System.out.println("Attempting To Save!");
			//Then Exit
			System.exit(0);
		}
		else
		{
			return;
		}
	}
	
	public void findSwimmerByNumber()
	{
		String result = JOptionPane.showInputDialog(this, "Please Enter The Number of Swimmer");
		
		int swimIndex = -1;
		
		try{
			swimIndex = Integer.parseInt(result);
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "You did not enter a number!");
			return;
		}
		
		swimIndex--;
		
		if(swimIndex >= 0 && swimIndex < SwimmerMasterList.size())
		{
			RecorderMain.viewSwimmer(SwimmerMasterList.getSwimmer(swimIndex));
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Swimmer " + (swimIndex + 1) + " does not exist!");
		}
	}
	
	public void findSwimmerByName()
	{
		String result = JOptionPane.showInputDialog(this, "Please Enter The Name of the Swimmwer (First Last)");
		
		String[] names = result.split(" ");
		
		if(names.length < 2)
		{
			JOptionPane.showMessageDialog(this, "Full Name Not Entered!");
			return;
		}
		
		if(SwimmerMasterList.hasSwimmers())
		{
			int swim;
			for(swim = 0; swim < SwimmerMasterList.size(); swim++)
			{
				Swimmer s = SwimmerMasterList.getSwimmer(swim);
				
				if(s.getFName().equalsIgnoreCase(names[0]) && s.getLName().equalsIgnoreCase(names[1]))
				{
					RecorderMain.viewSwimmer(s);
					return;
				}
			}
			
			JOptionPane.showMessageDialog(this, "Could Not Find Swimmer: " + result);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int but = -1;
		
		for(but = 0; but < buttons.length && !e.getSource().equals(buttons[but]); but++){}
		
		switch(but)
		{
		case 0: //Add Swimmer
			RecorderMain.switchToAddSwimmer();
			break;
		case 1: //Edit Swimmer
			break;
		case 2: //Find Swimmer Number
			findSwimmerByNumber();
			break;
		case 3: //Find Swimmer Name
			findSwimmerByName();
			break;
		case 4: //Remove
			attemptToRemoveSwimmer();
			break;
		case 5: //Save
			break;
		case 6: //Save As
			break;
		case 7: //Timesheet
			break;
		case 8: //chart
			break;
		case 9: //bookmarks
			break;
		case 10: //exit
			attemptClose();
			break;
		}

		
	}
}
