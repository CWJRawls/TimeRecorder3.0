package com.rawls.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class BookmarkProgressFrame extends JFrame {
	
	private static final int FRAME_WIDTH = 250;
	private static final int FRAME_HEIGHT = 100;
	private static final int BAR_WIDTH = 175;
	private static final int BAR_HEIGHT = 25;
	
	//non-gui fields
	private String message; //what will be shown as the current process
	private String barMsg; //what will be painted inside the progress bar
	
	//gui fields
	private JPanel panel;
	private JLabel label;
	private JProgressBar bar;
	
	public BookmarkProgressFrame(String msg, String barMsg)
	{
		//initialize the strings
		message = msg;
		this.barMsg = barMsg;
		
		//create the JPanel
		panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); //specifies the size the panel would like to be shown at
		
		//create the progress bar
		bar = new JProgressBar(0, 100);
		bar.setValue(0);
		bar.setString(barMsg);
		//panel.add(bar);
		bar.setVisible(true);
		
		//create the label
		label = new JLabel(message);
		
		//create the font for the label
		//Font labelFont = label.getFont();
		//Font newFont = new Font(labelFont.getFontName(), labelFont.getStyle(), 12);
		//label.setFont(newFont);
		
		//set the alignment for the label
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		//label.setBounds(0, 25, FRAME_WIDTH, FRAME_HEIGHT);
		
		panel.add(label, BorderLayout.CENTER);
		panel.setVisible(true);
		
		this.add(panel);
		//this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		//this.setResizable(false);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Bookmark Progress");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//panel.repaint();
		
		System.out.println("End Constructor");
		
	}
	
	public void setProgress(int value)
	{
		bar.setString("" + value + "%");
		if(value < bar.getMaximum())
			bar.setValue(value);
		else if(value >= bar.getMaximum())
		{
			bar.setValue(bar.getMaximum());
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); //kill the window when done
		}
		
	}
	
	public void updateLabel(String msg)
	{
		label.setText(msg);
	}
	
}
