package com.rawls.gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatePanel extends JPanel {

	private String[] texts = {"Please Enter", "Date of Meet", "or Time Trials"};
	private int[] textW;
	private int[] lSpacing = {0,0,0};
	private JLabel[] labels;
	
	private int[] labelScale = {200, 72};
	
	private int widest = 0;
	private int smallest = 9000;
	
	//pixels between labels
	private int labSpace = 24;
	
	public DatePanel()
	{
		//avoid attempts to auto layout panel
		this.setLayout(null);
		int verticalSpace = ((texts.length * labelScale[1]) + (texts.length - 1 * labSpace));
		
		Dimension d = WindowReference.getDimension();
		
		int topSpace = ((d.height - verticalSpace) / 2) - (verticalSpace / 2);
		
		int leftSpace = (int)(((d.width / 2) * 0.1) / 2);
		
		labels = new JLabel[texts.length];
		
		for(int i = 0; i < texts.length; i++)
		{
			labels[i] = new JLabel(texts[i]);
		}
		
		Font lFont = labels[0].getFont();
		int sWidth = labels[0].getFontMetrics(lFont).stringWidth(texts[2]);
		
		System.out.println("String Width: " + sWidth);
		
		int cWidth = (int)((d.width / 2) * 0.9);
		
		System.out.println("Width: " + cWidth);
		
		double wRatio = (double)cWidth / (double)sWidth;
		
		System.out.println("Width Ratio: " + wRatio);
		
		int nFontSize = (int)(lFont.getSize() * wRatio);
		
		System.out.println("Original Font Size: " + lFont.getSize() + " | New Font Size: " + nFontSize);
		
		Font nFont = new Font(lFont.getName(), Font.BOLD, nFontSize);
		
		while(labels[0].getFontMetrics(nFont).stringWidth(texts[2]) > cWidth)
		{
			nFontSize--;
			nFont = new Font(lFont.getName(), Font.BOLD, nFontSize);
			System.out.print(nFontSize + " | ");
		}
		
		System.out.println();
		
		textW = new int[3];
		
		for(int i = 0; i < texts.length; i++)
		{
			textW[i] = labels[0].getFontMetrics(nFont).stringWidth(texts[i]);
			System.out.print("Line " + i + ": " + textW[i] + " | ");
		}
		
		System.out.println();
		
		findCenteringSpace();
		
		for(int i = 0; i < labels.length; i++)
		{
			labels[i].setFont(nFont);
			labels[i].setBounds(leftSpace + lSpacing[i], (50 + (i * labels[i].getFontMetrics(nFont).getHeight()) + (i * labSpace)), textW[i], labels[i].getFontMetrics(nFont).getHeight());
			this.add(labels[i]);
		}
		
		Dimension curr = new Dimension(d.width / 2, d.height);
		
		System.out.println("Horizontal Space = " + labelScale[0] + " Left Space = " + leftSpace + " Total Horizontal = " + curr.width);
		
		this.setPreferredSize(curr);
	}
	
	private void findCenteringSpace()
	{
		widest = textW[0];
		
		for(int i = 0; i < textW.length; i++)
		{
			if(textW[i] > widest)
			{
				widest = textW[i];
				lSpacing[i] = 0;
				i = -1;

			}
			else if(textW[i] < widest)
			{
				lSpacing[i] = (widest - textW[i]) / 2;
			}
			else
			{
				lSpacing[i] = 0;
			}
		}
	}
	
}
