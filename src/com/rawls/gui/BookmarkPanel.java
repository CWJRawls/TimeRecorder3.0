package com.rawls.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.rawls.data.BookmarkGenerator;

public class BookmarkPanel extends JSplitPane implements ActionListener{

	/* ------gui components------- */
	//buttons
	private JButton previewBut;
	private JButton selectSaveBut;
	private JButton printBut;
	private JButton saveBut;
	private JButton nextB, prevB;
	
	//panels
	private JPanel prevPanel;
	private JPanel butPane;
	
	
	//textfields
	private JTextField saveField;
	private JTextField headerField;
	
	//drop down menus
	private JComboBox<String> fontBSizeBox;
	private JComboBox<String> fontBStyleBox;
	private JComboBox<String> fontHSizeBox;
	private JComboBox<String> fontHStyleBox;
	
	//labels
	private JLabel saveLocLab, bFontLab, hFontLab, currentPrevLab, headerLabel, prevNum;
	

	/* ----------data components------- */
	private BufferedImage[] bookmarks = null;
	
	private BufferedImage headerImage;
	
	private String header = "Summer 2016";
	
	private BookmarkGenerator bg = null;
	
	private int[] fSizes = {10,12,16,18,20,24,28,32,36,40,44,48,52,56,60,64,68,72};
	
	private int currPrevNum = 0;
	
	public BookmarkPanel(int w, int h)
	{
		//load the header image first.
		try{
			if(System.getProperty("os.name").toLowerCase().contains("mac"))
				headerImage = ImageIO.read(new File("./images/Tigershark Logo.png"));
			else
				headerImage = ImageIO.read(new File(".\\images\\Tigershark Logo.png"));
		}
		catch(IOException e)
		{
			System.out.println("Could not Find Logo");
			headerImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		}
		
		//begin setting up the gui for the panel
		this.setLayout(null);
		this.setPreferredSize(new Dimension(w, h));
		this.setDividerSize(10);
		this.setUI(new BasicSplitPaneUI() {
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
		
		butPane = new JPanel();
		butPane.setPreferredSize(new Dimension(500, 800));
		butPane.setLayout(null);
		this.setLeftComponent(butPane);

		prevPanel = new JPanel();
		prevPanel.setPreferredSize(new Dimension(500, 800));
		prevPanel.setLayout(null);
		this.setRightComponent(prevPanel);
		
		//check for default bookmark save location
		checkForDirectories();

		setupFileLocation();
		
		setupComboBoxes();
		
		setupButtons();
		
		setupHeader();
		
		createPreview();
		
		this.setDividerLocation(500);
	}

	
	private void checkForDirectories()
	{
		//create a file with a relative path that conforms to the system
		File bDir;
		
		if(System.getProperty("os.name").toLowerCase().contains("mac"))
		{
			bDir = new File("./Bookmarks");
		}
		else if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			bDir = new File(".\\Bookmarks");
		}
		else
		{
			bDir = new File("./Bookmarks");
		}
		
		//make the directory if it doesn't exist
		if(!bDir.exists())
		{
			bDir.mkdirs();
		}
	}
	
	private void setupComboBoxes()
	{
		fontBSizeBox = new JComboBox<String>();
		fontHSizeBox = new JComboBox<String>();
		for(int i = 0; i < fSizes.length; i++)
		{
			fontHSizeBox.addItem("" + fSizes[i]);
			fontBSizeBox.addItem("" + fSizes[i]);
		}
		
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBStyleBox = new JComboBox<String>();
		fontHStyleBox = new JComboBox<String>();
		
		for(int i = 0; i < fontNames.length; i++)
		{
			fontBStyleBox.addItem(fontNames[i]);
			fontHStyleBox.addItem(fontNames[i]);
		}
		
		fontBSizeBox.setBounds(40, 197, 200, 25);
		fontHSizeBox.setBounds(40, 327, 200, 25);
		fontBStyleBox.setBounds(260, 197, 200, 25);
		fontHStyleBox.setBounds(260, 327, 200, 25);
		
		butPane.add(fontBSizeBox);
		butPane.add(fontBStyleBox);
		butPane.add(fontHSizeBox);
		butPane.add(fontHStyleBox);

		bFontLab = new JLabel("Body Font Style");
		hFontLab = new JLabel("Header Font Style");
		
		bFontLab.setFont(new Font(bFontLab.getFont().getName(), 1, 18));
		hFontLab.setFont(new Font(hFontLab.getFont().getName(), 1, 18));
		
		bFontLab.setBounds(150, 167, 200, 25);
		hFontLab.setBounds(150, 297, 200, 25);
		
		fontBSizeBox.setSelectedIndex(fSizes.length - 1);
		fontHSizeBox.setSelectedIndex(fSizes.length - 1);
		
		butPane.add(bFontLab);
		butPane.add(hFontLab);
	}
	
	private void setupFileLocation()
	{
		String fPath;
		
		if(System.getProperty("os.name").toLowerCase().contains("mac"))
			fPath = "./Bookmarks/Temp.pdf";
		else if(System.getProperty("os.name").toLowerCase().contains("windows"))
			fPath  = ".\\Bookmarks\\Temp.pdf";
		else
			fPath = "./Bookmarks/Temp.pdf";
		
		File f = new File(fPath);
		
		saveField = new JTextField();
		saveField.setText(f.getAbsolutePath());
		saveField.setBounds(25, 97, 300, 25);
		butPane.add(saveField);
		
		
		selectSaveBut = new JButton("Browse");
		selectSaveBut.setBounds(350, 97, 125, 25);
		selectSaveBut.addActionListener(this);
		butPane.add(selectSaveBut);
		
		JLabel saveLocLab = new JLabel("Save Location");
		saveLocLab.setFont(new Font(saveLocLab.getFont().getName(), 1, 18));
		saveLocLab.setBounds(185, 62, 130, 25);
		butPane.add(saveLocLab);
	}
	
	private void setupButtons()
	{
		previewBut = new JButton("Generate Bookmarks");
		saveBut = new JButton("Save For Printing");
		printBut = new JButton("Print Bookmarks");
		
		previewBut.setBounds(150, 502, 200, 50);
		saveBut.setBounds(150, 580, 200, 50);
		printBut.setBounds(150, 658, 200, 50);
		
		previewBut.addActionListener(this);
		saveBut.addActionListener(this);
		printBut.addActionListener(this);
		
		printBut.setEnabled(false);
		
		butPane.add(previewBut);
		butPane.add(saveBut);
		butPane.add(printBut);
		
	}
	
	private void setupHeader()
	{
		headerField = new JTextField();
		headerField.setText(header);
		headerField.setBounds(100, 427, 300, 25);
		
		headerLabel = new JLabel("Header Text");
		headerLabel.setFont(new Font(headerLabel.getFont().getName(), 1, 18));
		headerLabel.setBounds((500 - headerLabel.getFontMetrics(headerLabel.getFont()).stringWidth("Header Text")) / 2, 397, headerLabel.getFontMetrics(headerLabel.getFont()).stringWidth("Header Text"), 25);
		
		butPane.add(headerField);
		butPane.add(headerLabel);
		
	}
	
	private void createPreview()
	{
		currentPrevLab = new JLabel(scalePreview(headerImage));
		
		currentPrevLab.setBounds(80, 20, 420, 675);
		
		prevPanel.add(currentPrevLab);
		
		nextB = new JButton(">>");
		nextB.addActionListener(this);
		nextB.setBounds(280, 727, 25, 20);
		
		prevPanel.add(nextB);
		
		prevB = new JButton("<<");
		prevB.addActionListener(this);
		prevB.setBounds(195, 727, 25, 20);
		
		prevPanel.add(prevB);
		
		prevNum = new JLabel("0/0");
		prevNum.setBounds(220, 727, 60, 25);
		prevNum.setFont(new Font(prevNum.getFont().getName(), Font.BOLD, 12));
		prevNum.setHorizontalAlignment(SwingConstants.CENTER);
		prevNum.setVerticalAlignment(SwingConstants.CENTER);
		
		prevPanel.add(prevNum);
	}
	
	private ImageIcon scalePreview(BufferedImage bi)
	{
		BufferedImage newImg = new BufferedImage(420, 675, bi.getType());
		
		Graphics2D g = newImg.createGraphics();
		
		int w = bi.getWidth();
		int h = bi.getHeight();
		
		if(w > 420 && (h < 675 || h < w))
		{
			double scale = 420.0 / (double) w;
			
			g.drawImage(bi, 0, 0, (int)(scale * w), (int)(scale * h), null);
		}
		else if(h > 675 && (w < 420 || w < h))
		{
			double scale = 675.0 / (double) h;
			
			g.drawImage(bi, 0, 0, (int)(scale * w), (int)(scale * h), null);
		}
		
		g.dispose();
		
		return new ImageIcon(newImg);
	}
	
	public boolean hasBookmarks()
	{
		if(bookmarks == null)
			return false;
		
		return true;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(previewBut))
		{
			//generate bookmarks
			Font b = new Font(fontBStyleBox.getItemAt(fontBStyleBox.getSelectedIndex()), Font.BOLD, Integer.parseInt(fontBSizeBox.getItemAt(fontBSizeBox.getSelectedIndex())));
			Font h = new Font(fontHStyleBox.getItemAt(fontHStyleBox.getSelectedIndex()), Font.BOLD, Integer.parseInt(fontHSizeBox.getItemAt(fontHSizeBox.getSelectedIndex())));
			bg = new BookmarkGenerator(h, b, headerImage, headerField.getText());
			bg.generateBookmarks();
			bookmarks = bg.getBookmarks();
			currentPrevLab.setIcon(scalePreview(bookmarks[0]));
			prevNum.setText("1/" + bookmarks.length);
			currPrevNum = 0;
		}
		else if(e.getSource().equals(selectSaveBut))
		{
			//launch file browser and change text field
			JFileChooser jfc = new JFileChooser();
			
			int selection = jfc.showSaveDialog(this);
			
			if(selection == JFileChooser.APPROVE_OPTION)
			{
				String newPath = jfc.getSelectedFile().getAbsolutePath();
				int i = newPath.length() - 1;
				while(newPath.charAt(i) != '.' && i > 0)
				{
					i--;
				}
				
				if(newPath.length() - i < newPath.length() - 5)
				{
					newPath += ".pdf";
				}
				else if(newPath.length() - i > newPath.length() - 5)
				{
					String ending = newPath.substring(i);
					
					if(!ending.equalsIgnoreCase(".pdf"))
					{
						newPath += ".pdf";
					}
				}
				
				saveField.setText(newPath);
				
			}
			
		}
		else if(e.getSource().equals(nextB))
		{
			if(this.hasBookmarks())
			{
				if(currPrevNum < bookmarks.length - 1)
				{
					currPrevNum++;
					currentPrevLab.setIcon(scalePreview(bookmarks[currPrevNum]));
					String num = currPrevNum + 1 + "";
					prevNum.setText(num + "/" + bookmarks.length);
				}
				else
				{
					currPrevNum = 0;
					currentPrevLab.setIcon(scalePreview(bookmarks[currPrevNum]));
					String num = currPrevNum + 1 + "";
					prevNum.setText(num + "/" + bookmarks.length);
				}
			}
		}
		else if(e.getSource().equals(prevB))
		{
			if(this.hasBookmarks())
			{
				if(currPrevNum > 0)
				{
					currPrevNum--;
					currentPrevLab.setIcon(scalePreview(bookmarks[currPrevNum]));
					String num = currPrevNum + 1 + "";
					prevNum.setText(num + "/" + bookmarks.length);
				}
				else
				{
					currPrevNum = bookmarks.length - 1;
					currentPrevLab.setIcon(scalePreview(bookmarks[currPrevNum]));
					String num = currPrevNum + 1 + "";
					prevNum.setText(num + "/" + bookmarks.length);
				}
			}
		}
		else if(e.getSource().equals(saveBut))
		{
			if(bg.hasBookmarks())
			{
				bg.saveAsPngs(saveField.getText());
				bg.createPDF(saveField.getText());
			}
		}
		
	}
}
