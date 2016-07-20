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

public class MainPanel extends JPanel implements ActionListener, TRComponent{
	
	//constants
	public static final int COMP_START = 0; //starting screen
	public static final int COMP_DATE = 1; //date entry screen
	public static final int COMP_TEAM = 2; //team view screen
	public static final int COMP_SWIMMER = 3; //Swimmer screen with list of records
	public static final int COMP_SWIM_RECORD = 4; //swimmer screen on left, record edit on right
	
	//Component Size Objects
	private Dimension dim;
	
	//Containers
	private JSplitPane jsp;
	
	//Panels to use
	StartPanelLogo spl;
	StartPanelButtons spb;
	DatePanel dp;
	DatePanelEntry dpe;
	TeamListPanel tlp;
	TeamButtonPanel tbp;
	
	
	//Status Tracker
	private int currentComp = COMP_START;
	
	public MainPanel()
	{
		dim = WindowReference.getDimension();
		
		System.out.println("Window Size: " + dim.getWidth() + "x" + dim.getHeight());
		this.setPreferredSize(dim);
		
		jsp = new JSplitPane();
		jsp.setDividerLocation(0.5);
		spl = new StartPanelLogo();
		jsp.setLeftComponent(spl);
		spb = new StartPanelButtons();
		jsp.setRightComponent(spb);
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

	public void switchToDateView()
	{	
		dp = new DatePanel();
		dpe = new DatePanelEntry();
		jsp.setRightComponent(dp);
		jsp.setLeftComponent(dpe);
		this.revalidate();
		this.repaint();
	}
	
	public void switchToTeamView()
	{
		tlp = new TeamListPanel();
		tbp = new TeamButtonPanel(tlp);
		jsp.setRightComponent(tlp);
		jsp.setLeftComponent(tbp);
		this.revalidate();
		this.repaint();
	}
	
	public void switchToSwimmerView(Swimmer s)
	{
		RecordListPanel rlp = new RecordListPanel(s);
		jsp.setLeftComponent(new SwimmerInfoPanel(rlp));
		jsp.setRightComponent(rlp);
		this.revalidate();
		this.repaint();
	}
	
	public void SwitchToAddSwimmer()
	{
		jsp.setRightComponent(new AddLabelPanel());
		jsp.setLeftComponent(new AddSwimmerPanel());
		this.revalidate();
		this.repaint();
	}
	
	public void switchToStartingView()
	{
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/* Why Did I do This? */
	}
	
	public void switchToChangeDate()
	{
		jsp.setRightComponent(new DatePanelEntry());
		this.revalidate();
		this.repaint();
	}

	@Override
	public void keyTyped(int kCode) {
		
		switch(currentComp)
		{
		case COMP_START:
			spl.keyTyped(kCode);
			spb.keyTyped(kCode);
			break;
		case COMP_DATE:
			dp.keyTyped(kCode);
			dpe.keyTyped(kCode);
			break;
		case COMP_TEAM:
			spl.keyTyped(kCode);
			spb.keyTyped(kCode);
			break;
		case COMP_SWIMMER:
			//spl.keyTyped(kCode);
			//spb.keyTyped(kCode);
			break;
		case COMP_SWIM_RECORD:
			//spl.keyTyped(kCode);
			//spb.keyTyped(kCode);
			break;
		}
	}

	@Override
	public void keyPressed(int kCode) {
		
		switch(currentComp)
		{
		case COMP_START:
			spl.keyPressed(kCode);
			spb.keyPressed(kCode);
			break;
		case COMP_DATE:
			dp.keyPressed(kCode);
			dpe.keyPressed(kCode);
			break;
		case COMP_TEAM:
			spl.keyPressed(kCode);
			spb.keyPressed(kCode);
			break;
		case COMP_SWIMMER:
			//spl.keyTyped(kCode);
			//spb.keyTyped(kCode);
			break;
		case COMP_SWIM_RECORD:
			//spl.keyTyped(kCode);
			//spb.keyTyped(kCode);
			break;
		}
		
	}

	@Override
	public void keyReleased(int kCode) {
		
		switch(currentComp)
		{
		case COMP_START:
			spl.keyReleased(kCode);
			spb.keyReleased(kCode);
			break;
		case COMP_DATE:
			dp.keyReleased(kCode);
			dpe.keyReleased(kCode);
			break;
		case COMP_TEAM:
			spl.keyReleased(kCode);
			spb.keyReleased(kCode);
			break;
		case COMP_SWIMMER:
			//spl.keyTyped(kCode);
			//spb.keyTyped(kCode);
			break;
		case COMP_SWIM_RECORD:
			//spl.keyTyped(kCode);
			//spb.keyTyped(kCode);
			break;
		}
		
	}


}
