package com.rawls.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.rawls.main.RecorderMain;

public class StartPanelButtons extends JPanel implements ActionListener{

	
	private JButton text, roster, about;
	
	private int[] butScale = {450, 75};
	private int[] butSpacing = {0, 113};
	private int[] edgeSpacing = {150, 150};
	private int[] butOrig = {150, 150};
	
	public StartPanelButtons()
	{
		//remove all attempts at controlling the layout
		this.setLayout(null);
		//get the current full dimension
		Dimension curr = (Dimension) WindowReference.getDimension().clone();
		//set the preferred size to fit one side of the window
		this.setPreferredSize(new Dimension(curr.width / 2, curr.height));
		//set the dimension to fit being on one side of the split pane
		curr.setSize(curr.getWidth() / 2, curr.getHeight());
		
		int[] dButScale = WindowReference.getNewDims(butScale[0], butScale[1]);
		int[] dButSpacing = WindowReference.getNewDims(butSpacing[0], butSpacing[1]);
		int[] dEdgeSpacing = WindowReference.getNewDims(edgeSpacing[0], edgeSpacing[1]);
		int[] dButOrig = WindowReference.getNewDims(butOrig[0], butOrig[1]);
		
		text = new JButton("Create New Team From Roster Text File");
		text.setBounds(dButOrig[0], dButOrig[1], dButScale[0], dButScale[1]);
		text.addActionListener(this);
		
		roster = new JButton("Load Existing Team File");
		roster.setBounds(dButOrig[0], dButOrig[1] + dButSpacing[1], dButScale[0], dButScale[1]);
		roster.addActionListener(this);
		
		about = new JButton("About TimeRecorder");
		about.setBounds(dButOrig[0], dButOrig[1] + (dButSpacing[1] * 2), dButScale[0], dButScale[1]);
		about.addActionListener(this);
		
		this.add(text);
		this.add(roster);
		this.add(about);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(text))
		{
			RecorderMain.changeToDatePanels(RecorderMain.OPEN_ROSTER);
		}
		else if(e.getSource().equals(roster))
		{
			RecorderMain.changeToDatePanels(RecorderMain.OPEN_TEAM);
		}
		else if(e.getSource().equals(about))
		{
			RecorderMain.changeToDatePanels(RecorderMain.OPEN_NEW);
		}
		
	}
}
