package com.rawls.gui;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InputNamePanel extends JPanel{

	private static final String FNAME_TEXT = "First";
	private static final String LNAME_TEXT = "Last";
	private static final String FIELD_WIDTH_STR = "WWWWWWWWWWWWWW"; //W is usually widest character in a font, so just using it to 
																			//get a width capable of containing most names
	
	private JTextField fnameField, lnameField;
	private JLabel fnameLabel, lnameLabel;
	
	public InputNamePanel() {
		//set the layout
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		//create the labels
		fnameLabel = new JLabel(FNAME_TEXT);
		lnameLabel = new JLabel(LNAME_TEXT);
		
		//align the text to be centered in the label
		fnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fnameLabel.setVerticalAlignment(SwingConstants.CENTER);
		lnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lnameLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		//get the size of the string to get how large to make the label
		Font lFont = fnameLabel.getFont();
		int width = getFontMetrics(lFont).stringWidth(FNAME_TEXT);
		width  = (int)(((float)width) * 1.1); //add 10% for some padding
		int height = getFontMetrics(lFont).getHeight(); 
		height = (int)(((float)height) * 1.1); //add 10% for some padding
		
		//set the size based on the math above
		Dimension lDimension = new Dimension(width, height);
		fnameLabel.setPreferredSize(lDimension);
		lnameLabel.setPreferredSize(lDimension);
		
		//create the textfields
		fnameField = new JTextField();
		fnameField.setText("Tempe");
		
		lnameField = new JTextField();
		lnameField.setText("Swimmer");
		
		//get the necessary width and height for the textfields
		Font fFont = fnameField.getFont();
		
		width = getFontMetrics(fFont).stringWidth(FIELD_WIDTH_STR);
		width = (int)(((float)width) * 1.1);
		height = getFontMetrics(fFont).getHeight();
		
		//set the size from the math above
		Dimension fDimension = new Dimension(width, height);
		fnameField.setPreferredSize(fDimension);
		lnameField.setPreferredSize(fDimension);
		
		//add all the components to the panel
		this.add(fnameLabel);
		this.add(fnameField);
		this.add(lnameLabel);
		this.add(lnameField);
		
		//set the size of the component to fit all of the parts together
		this.setPreferredSize(new Dimension((int)((2 * lDimension.getWidth()) + (2 * fDimension.getWidth())), (int)fDimension.getHeight()));
		
		//set so that any typing will start in the first name field
		fnameField.requestFocusInWindow();
	}
	
	//method to retrieve user input after the dialog has been confirmed
	public String[] getEntries()
	{
		String[] output = new String[2];
		
		output[0] = fnameField.getText();
		output[1] = lnameField.getText();
		
		return output;
	}
}
