package com.rawls.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class SwimmerInfoPanel extends JPanel {
	
	private RecordListPanel rlp;
	
	public SwimmerInfoPanel(RecordListPanel rlp)
	{
		this.rlp = rlp;
		
		Dimension curr = new Dimension(WindowReference.getDimension().width / 2, WindowReference.getDimension().height);
		this.setPreferredSize(curr);
	}

}
