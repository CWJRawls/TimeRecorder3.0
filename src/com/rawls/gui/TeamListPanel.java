package com.rawls.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.rawls.storage.SwimmerMasterList;

public class TeamListPanel extends JPanel implements TRComponent{
	
	private JList<String> teamList;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	
	
	//Variables to track key presses
	private int dKeySec = Calendar.getInstance().get(Calendar.SECOND);
	private int dKeyMili = Calendar.getInstance().get(Calendar.MILLISECOND);
	private String typedNumber = "";
	private Calendar cal = Calendar.getInstance();
	public static final int MAX_KEY_DELTA = 1000; //time in miliseconds
	
	public TeamListPanel()
	{
		//this.setLayout(new GridLayout(1, 1));
		this.setPreferredSize(new Dimension(WindowReference.getDimension().width / 2, (int)(WindowReference.getDimension().getHeight() * 0.925)));
		Vector<String> names = SwimmerMasterList.getListDisplay();
		
		for(int i = 0; i < names.size(); i++)
		{
			String index = (i + 1) + ". ";
			model.addElement(index + names.get(i));
		}
		
		teamList = new JList<String>(model);
		teamList.setSelectedIndex(0);
		teamList.setVisibleRowCount(30);
		
		
		JScrollPane listPane = new JScrollPane(teamList);
		
		this.add(listPane);
		listPane.setPreferredSize(new Dimension(WindowReference.getDimension().width / 2, (int)(WindowReference.getDimension().getHeight() * 0.925)));
		//listPane.revalidate();
	

		
		System.out.println("List Width: " + listPane.getWidth() + " | List Height: " + listPane.getHeight());
	}
	
	public int getSelectedIndex()
	{
		return teamList.getSelectedIndex();
	}
	
	public void updateList()
	{
		
		System.out.println("updating displayed list");
		
		model.removeAllElements();
		
		Vector<String> nList = SwimmerMasterList.getListDisplay();
		
		for(int i = 0; i < nList.size(); i++)
		{
			String element = (i + 1) + ". " + nList.get(i);
			model.addElement(element);
		}
		
		
		
		//teamList.setListData(nList);
		
		//teamList.setVisible(false);
		//teamList.setVisible(true);
	}
	
	private boolean isContinuous(int s, int ms)
	{
		int dist = 0;
		//figure out if the minute is wrapping around
		if(s - dKeySec < 0)
		{
			int tSec = 60 + (s -dKeySec);
			
			if(tSec > 1)
				return false;
			else if(tSec == 1)
			{
				dist += (1000 - dKeyMili);
				dist += ms;
				
				if(dist > MAX_KEY_DELTA)
					return false;
				else
					return true;
			}
			else
			{
				dist += ms - dKeyMili;
				
				if(dist > MAX_KEY_DELTA)
					return false;
				else
					return true;
			}
		}
		else
		{
			if(s - dKeySec > 1)
				return false;
			else if(s - dKeySec == 1)
			{
				dist += (1000 - dKeyMili);
				dist += ms;
				
				if(dist > MAX_KEY_DELTA)
					return false;
				
				return true;
			}
			
			return true;
			
		}
	}
	

	public void keyTyped(int kCode) {
		// TODO Auto-generated method stub
		System.out.println("Key Typed");
	}


	public void keyPressed(int kCode) {
		// TODO Auto-generated method stub
		System.out.println("Key Pressed");
	}


	public void keyReleased(int kCode) {
		
		String temp = "";
		
		System.out.println("Key Released");
		
		switch(kCode)
		{
		case KeyEvent.VK_0:
			temp += "0";
			break;
		case KeyEvent.VK_1:
			temp += "1";
			break;
		case KeyEvent.VK_2:
			temp += "2";
			break;
		case KeyEvent.VK_3:
			temp += "3";
			break;
		case KeyEvent.VK_4:
			temp += "4";
			break;
		case KeyEvent.VK_5:
			temp += "5";
			break;
		case KeyEvent.VK_6:
			temp += "6";
			break;
		case KeyEvent.VK_7:
			temp += "7";
			break;
		case KeyEvent.VK_8:
			temp += "8";
			break;
		case KeyEvent.VK_9:
			temp += "9";
			break;
		}	
		int ms = cal.get(Calendar.MILLISECOND);
		int s = cal.get(Calendar.SECOND);
		
		if(isContinuous(s, ms))
		{
			typedNumber += temp;
		}
		else
		{
			if(!temp.equals(""))
				typedNumber = temp;
		}
			
		int index = Integer.parseInt(typedNumber);
			
		index--;
			
		if(teamList.getModel().getSize() < index)
		{
			index = teamList.getModel().getSize() - 1;
		}
			
		teamList.setSelectedIndex(index);
		
		dKeySec = s;
		dKeyMili = ms;
		
	}


}
