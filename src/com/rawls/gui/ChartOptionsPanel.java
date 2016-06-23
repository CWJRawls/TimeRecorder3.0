package com.rawls.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.rawls.storage.SwimmerMasterList;

public class ChartOptionsPanel extends JPanel implements ActionListener{

	private boolean arrangeBy = false;
	
	private String[] event_list = {"25 Freestyle","25 Backstroke","25 Breaststroke","25 Butterfly","50 Freestyle","50 Backstroke",
			"50 Breaststroke","50 Butterfly","100 Freestyle","100 Backstroke","100 Breaststroke","100 Butterfly","100 IM","200 IM"};
	
	private Color[] base_colors = {new Color(255, 0, 0),new Color(0, 255, 0),new Color(0, 0, 255),new Color(255, 255, 0),new Color(255, 0, 255),
			new Color(0, 255, 255)};
	
	private JTabbedPane jtp;
	
	private JPanel[] panels = new JPanel[4];
	private JCheckBox[] swimmers;
	private JCheckBox[] events = new JCheckBox[14];
	private JScrollPane checkPane;
	private JButton mode_but;
	
	public ChartOptionsPanel()
	{
		
		//initialize the panels
		for(int i = 0; i < panels.length; i++)
		{
			panels[i] = new JPanel();
		}
		System.out.println("Creating the swimmer panel");
		//panel for selecting who to examine
		checkPane = createListofSwimmers();
		panels[0].setLayout(null);
		checkPane.setBounds(0, 0, 477, 450);
		panels[0].add(checkPane);
		
		
		System.out.println("Creating the events panel");
		//panel for choosing which events to look at
		panels[1].setLayout(null);
		panels[1].add(createEventList());
		
		//panel for selecting colors
		
		jtp = new JTabbedPane();
		jtp.addTab("Swimmers", panels[0]);
		jtp.addTab("Events", panels[1]);
		jtp.addTab("Colors & Arrangement", panels[2]);
		jtp.addTab("Create", panels[3]);
		jtp.setBounds(0, 0, 500, 500);
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(500, 500));
		this.add(jtp);
	}
	
	private JScrollPane createListofSwimmers()
	{
		Vector<String> n_swimmers = SwimmerMasterList.getListDisplay();
		
		if(n_swimmers.size() > 0)
		{
			for(int i = 0; i < n_swimmers.size(); i++)
			{
				if(!SwimmerMasterList.getSwimmer(n_swimmers.get(i)).hasRecords())
				{
					n_swimmers.remove(i);
					i--;
				}
			}
		}
		
		swimmers = new JCheckBox[n_swimmers.size() + 1];
		
		for(int i = 0; i < swimmers.length; i++)
		{
			if(i == 0)
			{
				swimmers[0] = new JCheckBox("Whole Team");
			}
			else
			{
				swimmers[i] = new JCheckBox(n_swimmers.get(i - 1));
			}
		}
		
		JScrollPane jsp = new JScrollPane();
		jsp.setLayout(null);
		
		for(int i = 0; i< swimmers.length; i++)
		{
			swimmers[i].setBounds(0, (i * 15) + 5, 200, 15);
			jsp.add(swimmers[i]);
		}
		
		swimmers[0].addActionListener(this);
		
		return jsp;
	}

	private JScrollPane createEventList()
	{
		
		JScrollPane jsp = new JScrollPane();
		jsp.setLayout(null);
		jsp.setBounds(0, 0, 477, 450);
		
		for(int i = 0; i < events.length; i++)
		{
			events[i] = new JCheckBox(event_list[i]);
			events[i].setBounds(0, (i * 15) + 5, 250, 15);
			jsp.add(events[i]);
		}
		
		return jsp;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(swimmers[0]))
		{
			if(swimmers[0].isSelected())
			{
				if(swimmers.length > 0)
				{
					for(int i = 1; i < swimmers.length; i++)
					{
						swimmers[i].setEnabled(false);
					}
				}
			}
			else
			{
				if(swimmers.length > 0)
				{
					for(int i = 1; i < swimmers.length; i++)
					{
						swimmers[i].setEnabled(true);
					}
				}
			}
		}
		
	}
	
	
}
