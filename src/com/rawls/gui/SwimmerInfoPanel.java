package com.rawls.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rawls.data.Record;
import com.rawls.data.Swimmer;
import com.rawls.main.RecorderMain;
import com.rawls.storage.SwimmerMasterList;

public class SwimmerInfoPanel extends JPanel implements ActionListener{
	
	public static final int MODE_INFO = 0;
	public static final int MODE_RECORD = 1;
	public static final int MODE_RECORD_EDIT = 2;
	
	private int dispMode = MODE_INFO;
	
	private RecordListPanel rlp;
	private JButton[] swimmerOptions = new JButton[5];
	private String[] sOptText = {"Add Time", "Edit Time", "Remove Time", "Change Swimmers", "Close Swimmer"};
	private JTextField[] sDetails = new JTextField[3];
	private JLabel[] infoLabel = new JLabel[3];
	private String[] infoText = {"First Name", "Last Name", "Age"};
	private JLabel butLabel;
	private String butText = "Options";
	
	private JComboBox<String>[] menus = new JComboBox[2];
	private String[] strokes = {"Freestyle", "Backstroke", "Breaststroke", "Butterfly, IM"};
	private String[] distances = {"25", "50", "100", "200"};
	private JTextField[] time = new JTextField[3];
	private JTextField[] date = new JTextField[3];
	private JButton[] recOptions = new JButton[2];
	private String[] recText = {"Apply", "Cancel"};
	private JLabel colon;
	private JLabel dot;
	private JLabel[] slash = new JLabel[2];
	private JLabel timeLab;
	private JLabel dateLab;
	
	private Record r = null;
	
	
	public SwimmerInfoPanel(RecordListPanel rlp)
	{
		this.rlp = rlp;
		
		Dimension curr = new Dimension(WindowReference.getDimension().width / 2, WindowReference.getDimension().height);
		this.setPreferredSize(curr);
		
		this.setLayout(null);

		sDetails[0] = new JTextField();
		sDetails[0].setText(rlp.getSwimmer().getFName());
		sDetails[0].setBounds((int)(curr.width * 0.1), (int)(curr.height * 0.1), (int)(curr.width * 0.3), (int)(sDetails[0].getFontMetrics(sDetails[0].getFont()).getHeight() * 1.5));
		sDetails[1] = new JTextField();
		sDetails[1].setText(rlp.getSwimmer().getLName());
		sDetails[1].setBounds((int)((curr.width * 0.1) + (curr.width * 0.3) + (curr.width * 0.05)), (int)(curr.height * 0.1), (int)(curr.width * 0.3), (int)(sDetails[0].getFontMetrics(sDetails[0].getFont()).getHeight() * 1.5));
		sDetails[2] = new JTextField();
		sDetails[2].setText(rlp.getSwimmer().getAge() + "");
		sDetails[2].setBounds((int)((curr.width * 0.1) + ((curr.width * 0.3) * 2)+ ((curr.width * 0.05) * 2)), (int)(curr.height * 0.1), (int)(curr.width * 0.1), (int)(sDetails[0].getFontMetrics(sDetails[0].getFont()).getHeight() * 1.5));

		this.add(sDetails[0]);
		this.add(sDetails[1]);
		this.add(sDetails[2]);
		
		for(int i = 0; i < infoLabel.length; i++)
		{
			infoLabel[i] = new JLabel(infoText[i]);
			infoLabel[i].setBounds((int)(sDetails[i].getBounds().x + (curr.width * 0.01)), (int)(sDetails[i].getBounds().y - (curr.height * 0.05)), sDetails[i].getBounds().width, sDetails[i].getBounds().height);
			this.add(infoLabel[i]);
		}
		
		int soWidth = (int)(curr.width * 0.4);
		int soLeft = (int)(curr.width * 0.3);
		int soTop = (int)(curr.height * 0.3);
		int soHeight = (int)(curr.height * 0.075);
		int soBlock = (int)(curr.height * 0.09);
		
		for(int i = 0; i < swimmerOptions.length; i++)
		{
			swimmerOptions[i] = new JButton(sOptText[i]);
			swimmerOptions[i].addActionListener(this);
			swimmerOptions[i].setBounds(soLeft, soTop + (i * soBlock), soWidth, soHeight);
			this.add(swimmerOptions[i]);
		}
		
		butLabel = new JLabel(butText);
		butLabel.setBounds((int)(swimmerOptions[0].getBounds().x + (curr.width * 0.15)), (int)(swimmerOptions[0].getBounds().y - (curr.height * 0.075)), swimmerOptions[0].getBounds().width, swimmerOptions[0].getBounds().height);
		this.add(butLabel);
		
		/*-------------------------------*/
		
		int rSpace = (int)(curr.height * 0.075);
		
		menus[0] = new JComboBox<String>(distances);
		menus[1] = new JComboBox<String>(strokes);
		
		int mWidth = (int)(curr.width * 0.3);
		int mLeft = (int)(curr.width * 0.15);
		int mSpace = (int)(curr.width * 0.1);
		
		for(int i = 0; i < menus.length; i++)
		{
			menus[i].setBounds((mWidth * i) + (mSpace * i) + mLeft, (int)(curr.height * 0.35), mWidth, (int)(menus[i].getFontMetrics(menus[i].getFont()).getHeight() * 1.25));
			this.add(menus[i]);
			menus[i].setVisible(false);
		}
		
		int fWidth = (int)(curr.width * 0.22);
		int fSpace = (int)(curr.width * 0.02);
		int fLeft = (int)(curr.width * 0.15);
		
		for(int i = 0; i < time.length; i++)
		{
			time[i] = new JTextField();
			time[i].setBounds(fLeft + (i * fWidth) + (i * fSpace), menus[0].getBounds().y + menus[0].getBounds().height + rSpace, fWidth, (int)(time[i].getFontMetrics(time[i].getFont()).getHeight() * 1.25));
			this.add(time[i]);
			time[i].setVisible(false);
			date[i] = new JTextField();
			date[i].setBounds(fLeft + (i * fWidth) + (i * fSpace), menus[0].getBounds().y + (menus[0].getBounds().height * 2) + (rSpace * 2), fWidth, (int)(date[i].getFontMetrics(date[i].getFont()).getHeight() * 1.25));
			this.add(date[i]);
			date[i].setVisible(false);
		}
		
		int rbWidth = (int)(curr.width * 0.25);
		int rbLeft = fLeft;
		
		for(int i = 0; i < recOptions.length; i++)
		{
			recOptions[i] = new JButton(recText[i]);
			recOptions[i].setBounds(rbLeft + (i * rbWidth) + (int)(i * (curr.width * 0.2)), date[0].getBounds().y + date[0].getBounds().height + rSpace, rbWidth, (int)(curr.height * 0.075));
			this.add(recOptions[i]);
			recOptions[i].setVisible(false);
			recOptions[i].addActionListener(this);
		}
		
		colon = new JLabel(":");
		colon.setBounds((int)(time[1].getBounds().x - (curr.width * 0.015)), time[1].getBounds().y, colon.getFontMetrics(colon.getFont()).stringWidth(":"), colon.getFontMetrics(colon.getFont()).getHeight());
		dot = new JLabel(".");
		dot.setBounds((int)(time[2].getBounds().x - (curr.width * 0.015)), time[2].getBounds().y, colon.getFontMetrics(colon.getFont()).stringWidth("."), colon.getFontMetrics(colon.getFont()).getHeight());
		
		this.add(colon);
		this.add(dot);
		
		colon.setVisible(false);
		dot.setVisible(false);
		
		for(int i = 0; i < slash.length; i++)
		{
			slash[i] = new JLabel("/");
			slash[i].setBounds((int)(date[i + 1].getBounds().x - (curr.width * 0.015)), date[i + 1].getBounds().y, colon.getFontMetrics(colon.getFont()).stringWidth("/"), colon.getFontMetrics(colon.getFont()).getHeight());
			this.add(slash[i]);
			slash[i].setVisible(false);
		}
		
		timeLab = new JLabel("Time:");
		timeLab.setBounds(time[0].getBounds().x, time[0].getBounds().y - 20, timeLab.getFontMetrics(timeLab.getFont()).stringWidth("Time:"), timeLab.getFontMetrics(timeLab.getFont()).getHeight());
		dateLab = new JLabel("Date:");
		dateLab.setBounds(date[0].getBounds().x, date[0].getBounds().y - 20, timeLab.getFontMetrics(timeLab.getFont()).stringWidth("Time:"), timeLab.getFontMetrics(timeLab.getFont()).getHeight());
		this.add(timeLab);
		this.add(dateLab);
		
		timeLab.setVisible(false);
		dateLab.setVisible(false);
	}
	
	private void editRecord(Record r)
	{
		dispMode = MODE_RECORD_EDIT;
		changeToRecordElements();
		
		this.r = r;
		
		setRecordInfo(r);
	}
	
	private void addRecord()
	{
		dispMode = MODE_RECORD;
		changeToRecordElements();
		
		r = new Record();
		
		setRecordInfo(r);
		
		
	}
	
	private void saveRecordChanges(Record r)
	{
		
	}
	
	private void addRecordToSwimmer()
	{
		Swimmer s = rlp.getSwimmer();
		s.addRecord(r);
	}
	
	private void switchToInfoElements()
	{
		dispMode = MODE_INFO;
		
		for(int i = 0; i < swimmerOptions.length; i++)
		{
			swimmerOptions[i].setVisible(true);
		}
		
		butLabel.setVisible(true);
		
		for(int i = 0; i < time.length; i++)
		{
			time[i].setVisible(false);
			date[i].setVisible(false);
		}
		
		for(int i = 0; i < menus.length; i++)
		{
			menus[i].setVisible(false);
			recOptions[i].setVisible(false);
			slash[i].setVisible(true);
		}
		
		colon.setVisible(false);
		dot.setVisible(false);
		timeLab.setVisible(false);
		dateLab.setVisible(false);
	}
	
	private void changeToRecordElements()
	{
		for(int i = 0; i < swimmerOptions.length; i++)
		{
			swimmerOptions[i].setVisible(false);
		}
		
		butLabel.setVisible(false);
		
		for(int i = 0; i < time.length; i++)
		{
			time[i].setVisible(true);
			date[i].setVisible(true);
		}
		
		for(int i = 0; i < menus.length; i++)
		{
			menus[i].setVisible(true);
			recOptions[i].setVisible(true);
			slash[i].setVisible(true);
		}
		
		colon.setVisible(true);
		dot.setVisible(true);
		timeLab.setVisible(true);
		dateLab.setVisible(true);
	}
	
	private int[] getEventParts(String s)
	{
		String[] parts = s.split(" ");
		int[] output = new int[2];
		int i = 0;
		for(i = 0; i < distances.length && !parts[0].equals(distances[i]); i++)
		{}
		
		output[0] = i;
		
		for(i = 0; i < strokes.length && !parts[1].equals(strokes[i]); i++)
		{}
		
		output[1] = i;
		
		return output;
	}
	
	private void setRecordInfo(Record r)
	{
		int[] ePart = getEventParts(r.getEvent());
		
		menus[0].setSelectedIndex(ePart[0]);
		menus[1].setSelectedIndex(ePart[1]);
		
		int[] dateP = new int[3];
		
		if(dispMode == MODE_RECORD)
		{
			dateP = RecorderMain.getDate();
		}
		else
		{
			dateP = new int[3];
			dateP[0] = r.getDay();
			dateP[1] = r.getMonth();
			dateP[2] = r.getYear();
		}
		
		date[0].setText("" + dateP[0]);
		date[1].setText("" + dateP[1]);
		date[2].setText("" + dateP[2]);
		
		time[0].setText("" + r.getMins());
		time[1].setText("" + r.getSec());
		time[2].setText("" + r.getMili());
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(dispMode == MODE_INFO)
		{
			int but = -1;
			
			for(but = 0; but < swimmerOptions.length && !e.getSource().equals(swimmerOptions[but]); but++){}
			
			switch(but)
			{
			case 0:
				addRecord();
				break;
			case 1:
				editRecord(rlp.getSwimmer().getRecord(rlp.getSelectedIndex()));
				break;
			case 2:
				int result =JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this time?");
				
				if(result == JOptionPane.YES_OPTION)
				{
					rlp.getSwimmer().removeRecord(rlp.getSelectedIndex());
					rlp.updateList();
				}
				break;
			case 3:
				
				Swimmer s = rlp.getSwimmer();
				s.setFName(sDetails[0].getText());
				s.setLName(sDetails[1].getText());
				
				try{
					int nAge = Integer.parseInt(sDetails[2].getText());
					s.setAge(nAge);
				}catch(Exception e2)
				{
					JOptionPane.showMessageDialog(this, "Error! Age is not a number!");
				}
				
				Vector<String> sList = SwimmerMasterList.getListDisplay();
				String[] nList = new String[sList.size()];
				sList.copyInto(nList);
				String sResult = (String) JOptionPane.showInputDialog(this, "Select a New Swimmer to View", "Change Swimmers", JOptionPane.QUESTION_MESSAGE, null, nList, nList[0]);
		
				for(int i = 0; i < nList.length && sList != null; i++)
				{
					if(sResult.equals(nList[i]))
					{
						RecorderMain.viewSwimmer(SwimmerMasterList.getSwimmer(i));
						return;
					}
				}
				break;
			case 4:
				Swimmer s1 = rlp.getSwimmer();
				s1.setFName(sDetails[0].getText());
				s1.setLName(sDetails[1].getText());
				
				try{
					int nAge = Integer.parseInt(sDetails[2].getText());
					s1.setAge(nAge);
				}catch(Exception e2)
				{
					JOptionPane.showMessageDialog(this, "Error! Age is not a number!");
				}
				RecorderMain.changeToTeamView();
				break;
			}
		}
		else 
		{
			int but = -1;
			
			for(but = 0; but < recOptions.length && !e.getSource().equals(recOptions[but]); but++){}
			
			switch(but)
			{
			case 0:
				try{
					int mili = Integer.parseInt(time[2].getText());
					int sec = Integer.parseInt(time[1].getText());
					int mins = Integer.parseInt(time[0].getText());
					r.setTime(mins, sec, mili);
				}catch(Exception e1)
				{
					JOptionPane.showMessageDialog(this, "Error Time is not a number!");
					return;
				}
				
				String event = (String)menus[0].getSelectedItem() + " "  + (String)menus[1].getSelectedItem();
				r.setEvent(event);
				
				try{
					int year = Integer.parseInt(date[2].getText());
					int month = Integer.parseInt(date[1].getText());
					int day = Integer.parseInt(date[0].getText());
					r.setDate(day, month, year);
				}catch(Exception e1)
				{
					JOptionPane.showMessageDialog(this, "Error Date is not a number!");
					return;
				}
				
				if(dispMode == MODE_RECORD)
					addRecordToSwimmer();
				rlp.updateList();
				switchToInfoElements();
				break;
				
			case 1:
				switchToInfoElements();
				break;
			}
		}
		
	}

}
