package com.rawls.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.rawls.data.Swimmer;

public class MainPanel extends JPanel implements ActionListener{
	
	//Component Size Objects
	private Dimension dim;
	
	//Containers
	private JSplitPane jsp;
	
	public MainPanel()
	{
		dim = WindowReference.getDimension();
		
		System.out.println("Window Size: " + dim.getWidth() + "x" + dim.getHeight());
		this.setPreferredSize(dim);
		
		jsp = new JSplitPane();
		jsp.setDividerLocation(0.5);
		jsp.setLeftComponent(new StartPanelLogo());
		jsp.setRightComponent(new StartPanelButtons());
		jsp.setBounds(0, 0, this.getWidth(), this.getHeight());
		jsp.setDividerSize(10);
		jsp.setUI(new BasicSplitPaneUI() {
			public BasicSplitPaneDivider createDefaultDivider(){
				return new BasicSplitPaneDivider(this)
						{
					public void setBorder(Border b){
					}
					
					@Override
					public void paint(Graphics g)
					{
						g.setColor(Color.BLACK);
						g.fillRect(0, 0, getSize().width, getSize().height);
						super.paint(g);
					}
					};};
						});
		
		this.add(jsp);
	}

	
	public void switchToTeamView()
	{
		
	}
	
	public void switchToSwimmerView(Swimmer s)
	{
		
	}
	
	public void switchToStartingView()
	{
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
