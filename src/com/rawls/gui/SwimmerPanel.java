package com.rawls.gui;

import javax.swing.JPanel;

import com.rawls.data.Swimmer;

/**
 * 
 * @author Connor Rawls
 *
 *Class To be displayed on the right side of the split pane with a list of times.
 */

public class SwimmerPanel extends JPanel{

	int fSize, activeRecord;
	String fName, lName; //keep track of the original name when the swimmer was loaded.
	String dFName, dLName; //keep track of what has been entered
	Swimmer swim;
	
	
	public SwimmerPanel(Swimmer s, int fSize)
	{
		swim = s;
		this.fSize = fSize;
	}
	
}
