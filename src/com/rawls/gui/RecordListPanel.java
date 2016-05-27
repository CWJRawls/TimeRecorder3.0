package com.rawls.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.rawls.data.Swimmer;

public class RecordListPanel extends JPanel {

	private JList<String> recordList;
	private Swimmer s;
	
	
	public RecordListPanel(Swimmer s)
	{
		this.s = s;
		Vector<String> records = s.getRecordList();
		
		recordList = new JList<String>(records);
		recordList.setSelectedIndex(0);
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane listPane = new JScrollPane(recordList);
		
		this.setLayout(new GridLayout(1,1));
		
		this.add(listPane);
		
		Dimension d = WindowReference.getDimension();
		Dimension curr = new Dimension(d.width / 2, d.height);
		
		this.setPreferredSize(curr);
		
	}
	
	public int getSelectedIndex()
	{
		String s = recordList.getSelectedValue();
		
		if(!s.equalsIgnoreCase("No Records"))
			return recordList.getSelectedIndex();
		else
			return -1;
	}
	
	public void updateList()
	{
		recordList.setListData(s.getRecordList());
		recordList.setSelectedIndex(0);
	}
	
	public Swimmer getSwimmer()
	{
		return s;
	}
	
}
