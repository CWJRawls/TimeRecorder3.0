package com.rawls.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rawls.data.Record;

public class SwimmerInfoPanel extends JPanel implements ActionListener{
	
	public static final int MODE_INFO = 0;
	public static final int MODE_RECORD = 1;
	
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
	private String[] strokes = {"Freestyle", "Backstroke", "Breaststroke", "Butterfly"};
	private String[] distances = {"25", "50", "100", "200"};
	private JTextField[] time = new JTextField[3];
	private JTextField[] date = new JTextField[3];
	private JButton[] recOptions = new JButton[2];
	private String[] recText = {"Apply", "Cancel"};
	
	
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
		}
		
	}
	
	private void editRecord(Record r)
	{
		
	}
	
	private void addRecord()
	{
		
	}
	
	private void saveRecordChanges(Record r)
	{
		
	}
	
	private void switchToInfoElements()
	{
		
	}
	
	private void changeToRecordElements(Record r)
	{
		
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
			}
		}
		else if(dispMode == MODE_RECORD)
		{
			
		}
		
	}

}
