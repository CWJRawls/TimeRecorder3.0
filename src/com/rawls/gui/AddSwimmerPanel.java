package com.rawls.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rawls.data.Swimmer;
import com.rawls.main.RecorderMain;
import com.rawls.storage.SwimmerMasterList;

public class AddSwimmerPanel extends JPanel implements ActionListener{

	//Top Component
	private JLabel topLabel;
	private String topText = "Adding A Swimmer";
	
	//Labels for text fields
	private JLabel[] fieldLabels = new JLabel[3];
	private String[] labelText = {"First Name", "Last Name", "Age"};
	
	//buttons for bottom of component
	private JButton[] buttons = new JButton[2];
	private String[] butText = {"Add", "Cancel"};
	
	//text fields
	private JTextField[] fields = new JTextField[3];
	private String[] fieldText = {"Tempe", "Swimmer", "" + Calendar.getInstance().get(Calendar.YEAR)};
	
	//percent of vertical component spacing between component groups
	private double cSpacePercent = 0.05;
	
	public AddSwimmerPanel()
	{
		/*----- Setup General Panel -----*/
		/*-------------------------------*/
		this.setLayout(null);
		
		Dimension d = WindowReference.getDimension();
		Dimension curr = new Dimension(d.width / 2, d.height);
		this.setPreferredSize(curr);
		
		int cSpace = (int)(curr.height * cSpacePercent);
		
		/*---- Create Label At Top of Panel ----*/
		/*--------------------------------------*/
		
		topLabel = new JLabel(topText);
		
		Font tlFont = topLabel.getFont();
		
		int tlWidth = (int)(curr.width * 0.6);
		
		while(topLabel.getFontMetrics(tlFont).stringWidth(topText) < (tlWidth * 0.9))
		{
			tlFont = new Font(tlFont.getName(), Font.PLAIN, tlFont.getSize() + 1);
		}
		
		int tlLeft = (int)(curr.width * 0.2);
		int tlTop = (int)(curr.height * 0.2);
		
		topLabel.setFont(tlFont);
		topLabel.setBounds(tlLeft, tlTop, tlWidth, topLabel.getFontMetrics(tlFont).getHeight());
		
		this.add(topLabel);
		
		/*---- Create Labels Next To Text Fields ----*/
		/*-------------------------------------------*/
		
		int fSpace = (int)(curr.height * 0.02);
		int fHeight = (int)(curr.height * 0.075);
		
		Font flFont = new Font(tlFont.getName(), Font.PLAIN, 8);
		
		fieldLabels[0] = new JLabel(labelText[0]);
		
		while(fieldLabels[0].getFontMetrics(flFont).getHeight() < fHeight && fieldLabels[0].getFontMetrics(flFont).stringWidth(labelText[0]) < (curr.width * 0.25))
		{
			flFont = new Font(flFont.getName(), Font.PLAIN, flFont.getSize() + 1);
		}
		
		for(int i = 0; i < fieldLabels.length; i++)
		{
			fieldLabels[i] = new JLabel(labelText[i]);
			fieldLabels[i].setFont(flFont);
			fieldLabels[i].setBounds((int)(curr.width * 0.1), tlTop + topLabel.getFontMetrics(tlFont).getHeight() + cSpace + (i * fHeight) + (i * fSpace), (int)(curr.width * 0.3), fHeight);
			this.add(fieldLabels[i]);
		}
		
		/*------ Create Text Fields -------*/
		/*---------------------------------*/
		
		int tfLeft = (int)(curr.getWidth() * 0.4);
		
		for(int i = 0; i < fields.length; i++)
		{
			fields[i] = new JTextField();
			fields[i].setText(fieldText[i]);
			fields[i].setFont(flFont);
			fields[i].setBounds(tfLeft, tlTop + topLabel.getFontMetrics(tlFont).getHeight() + cSpace + (i * fHeight) + (i * fSpace), (int)(curr.width * 0.5), fHeight);
			this.add(fields[i]);
		}
		
		/*-- Create Buttons At The Bottom of the Component --*/
		/*---------------------------------------------------*/
		
		int bWidth = (int)(curr.width * 0.25);
		int bHeight = (int)(topLabel.getFontMetrics(flFont).getHeight() * 1.25);
		int bSpace = (int)(curr.width * 0.1);
		int bTop = fields[2].getBounds().y + fields[2].getBounds().height + cSpace;
		
		for(int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton(butText[i]);
			buttons[i].setFont(flFont);
			buttons[i].setBounds(tlLeft + (i * bWidth) + (i * bSpace), bTop, bWidth, bHeight);
			buttons[i].addActionListener(this);
			this.add(buttons[i]);
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int but;
		for(but = 0; but < buttons.length && !e.getSource().equals(buttons[but]); but++)
		{
			/* Do Absolutely Nothing */
		}
		
		//Attempt to get data and add a swimmer
		if(but == 0)
		{
			String fName = fields[0].getText();
			String lName = fields[1].getText();
			int age = 2016;
			
			try{
				age = Integer.parseInt(fields[2].getText());
			}
			catch(Exception ee)
			{
				JOptionPane.showMessageDialog(this, "Error Reading Age! Please Enter A Number!");
				return;
			}
			
			SwimmerMasterList.addSwimmer(new Swimmer(fName, lName, age));
		}
		
		RecorderMain.changeToTeamView();
	}

}
