package com.rawls.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rawls.main.RecorderMain;

public class DatePanelEntry extends JPanel implements ActionListener, TRComponent {
	
	private JButton go;
	private JTextField[] fields = new JTextField[3];
	private JLabel[] instLabels = new JLabel[3];
	private JLabel[] slashLabels = new JLabel[2];
	private JLabel instruct;
	
	private int cSpace = 10;
	
	private String instruction = "Date of Meet / Time Trials";
	private String[] fLabels = {"DD", "MM", "YYYY"};
	
	public DatePanelEntry()
	{
		Dimension d = WindowReference.getDimension();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(d.width / 2, d.height));
		
		/*----------------------- Create The Instruction Label -------------------------*/
		/*------------------------------------------------------------------------------*/
		
		instruct = new JLabel(instruction);

		int cWidth = (int)((d.width / 2) * 0.9);
		
		Font iFont = getInstructionsFont(cWidth);
		
		instruct.setFont(iFont);
		
		int instX = ((d.width / 2) - cWidth) / 2;
		int instY = (int)(d.height * 0.25);
		
		instruct.setBounds(instX, instY, cWidth, instruct.getFontMetrics(iFont).getHeight());
		
		this.add(instruct);
		
		/*--------------------- Create the JTextFields For Entering The Date ------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		
		fields[0] = new JTextField();
		fields[0].setText("" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		if(fields[0].getText().length() < 2)
		{
			String text = "0" + fields[0].getText();
			fields[0].setText(text);
		}		
		fields[1] = new JTextField();
		fields[1].setText("" + (Calendar.getInstance().get(Calendar.MONTH) + 1));
		if(fields[1].getText().length() < 2)
		{
			String text = "0" + fields[1].getText();
			fields[1].setText(text);
		}
		fields[2] = new JTextField();
		fields[2].setText("" + Calendar.getInstance().get(Calendar.YEAR));
		
		Font tFont = new Font(iFont.getName(), Font.PLAIN, 24);
		
		int fWidth = (int) (fields[0].getFontMetrics(tFont).stringWidth("9999") * 1.5);
		
		int fSpace = (cWidth - (fWidth * 3)) / 4;
		int h = (int)(fields[0].getFontMetrics(tFont).getHeight() * 1.25);
		int fieldY = (h) + (cSpace * 2) + instruct.getFontMetrics(iFont).getHeight() + instY;
		int fieldX = instX + fSpace;
		
		for(int i = 0; i < fields.length; i++)
		{
			fields[i].setFont(tFont);
			fields[i].setBounds(fieldX + (fWidth * i) + (fSpace * i), fieldY, fWidth, h);
			fields[i].setHorizontalAlignment(JTextField.RIGHT);
			this.add(fields[i]);
		}
		
		/*------------------------ Create Labels Above Text Fields -----------------------*/
		/*--------------------------------------------------------------------------------*/
		
		for(int i = 0; i < instLabels.length; i++)
		{
			instLabels[i] = new JLabel(fLabels[i]);
			instLabels[i].setFont(tFont);
			int width = instLabels[i].getFontMetrics(tFont).stringWidth(fLabels[i]);
			int dWidth = (Math.abs(fields[i].getBounds().width - width)) / 2;
			instLabels[i].setBounds(fields[i].getBounds().x + dWidth, fields[i].getBounds().y - cSpace - instLabels[i].getFontMetrics(tFont).getHeight(), width, instLabels[i].getFontMetrics(tFont).getHeight());
			this.add(instLabels[i]);
		}
		
		/*----------------------- Slash Labels Between TextFields -----------------------*/
		/*-------------------------------------------------------------------------------*/
		
		for(int i = 0; i < slashLabels.length; i++)
		{
			slashLabels[i] = new JLabel("/");
			slashLabels[i].setFont(tFont);
			int sWidth = slashLabels[i].getFontMetrics(tFont).stringWidth("/");
			int sX = fields[i + 1].getBounds().x - (fSpace / 2) - (sWidth / 2);
			int sY = fields[i + 1].getBounds().y;
			slashLabels[i].setBounds(sX, sY, sWidth, slashLabels[i].getFontMetrics(tFont).getHeight());
			this.add(slashLabels[i]);
		}
		
		/*------------------ Create JButton To Submit Data ------------------*/
		/*-------------------------------------------------------------------*/
		
		go = new JButton("GO");
		
		go.setFont(tFont);
		
		go.setBounds(instX, fields[0].getBounds().y + fields[0].getBounds().height + cSpace, cWidth,(int)(go.getFontMetrics(tFont).getHeight() * 2.0));
		
		go.addActionListener(this);
		
		this.add(go);
	}
	
	private Font getInstructionsFont(int cWidth)
	{
		Font lFont = instruct.getFont();
		int sWidth = instruct.getFontMetrics(lFont).stringWidth(instruction);
		
		double wRatio = (double)cWidth / (double)sWidth;
		
		int iFontSize = (int)(lFont.getSize() * wRatio);
		
		Font iFont = new Font(lFont.getName(), Font.BOLD, iFontSize);
		
		while(instruct.getFontMetrics(iFont).stringWidth(instruction) > cWidth)
		{
			iFontSize--;
			iFont = new Font(lFont.getName(), Font.BOLD, iFontSize);
		}
		
		return iFont;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(go))
		{
			String date = "";
			
			for(int i = 0; i < fields.length; i++)
			{
				date += fields[i].getText();
				
				if(i < fields.length - 1)
					date += "/";
			}
			
			RecorderMain.changeDate(date);
			RecorderMain.changeToTeamView();
		}
		
	}

	@Override
	public void keyTyped(int kCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int kCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int kCode) {
		// TODO Auto-generated method stub
		
	}

}
