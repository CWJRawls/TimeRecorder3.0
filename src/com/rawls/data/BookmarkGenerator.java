package com.rawls.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.rawls.storage.SwimmerMasterList;

public class BookmarkGenerator {

	//Constants for bookmark generation
	private static final int LINE_SPACE = 5;
	private static final int HEAD_SIZE = 250;
	private static final int HEAD_WIDTH = 550;
	private static final int TEXT_MARGIN = 25;
	private static final int BODY_WIDTH = 580;
	private static final int BODY_MARGIN = 10;
	private static final int BODY_HEIGHT = 600;
	
	//constants for placing bookmarks into pages
	private static final int PAGE_WIDTH = 1845;
	private static final int PAGE_HEIGHT = 2715;
	private static final int PAGE_SPACE = 15;
	
	private Font head, body;
	private BufferedImage header;
	private BufferedImage[] bookmarks = null;
	private String headerText;
	
	
	public BookmarkGenerator(Font head, Font body, BufferedImage header, String hText)
	{
		this.head = head;
		this.body = body;
		this.header = header;
		headerText = hText;
	}
	
	public void generateBookmarks()
	{
		//check the size of the header image
		int w = header.getWidth();
		int h = header.getHeight();
		
		//resize the header if needed
		if(w > 580 || h > 480)
		{
			if(w > 580 && (h <= 480 || h <= w))
			{
				double sFactor = 580.0 / (double)w;
				header = resizeHeader(sFactor, w, h);
			}
			else if(h > 480 && (w <= 580 || w <= h))
			{
				double sFactor = 480.0 / (double)h;
				header = resizeHeader(sFactor, w, h);
			}
		}
		
		//check the new size of the header image
		w = header.getWidth();
		h = header.getHeight();
		
		int wOffset = (600 - w) / 2;
		int hOffset = (500 - h) / 2;
		
		bookmarks = new BufferedImage[SwimmerMasterList.getList().size()];
		
		ArrayList<Swimmer> fastest = SwimmerMasterList.getFastestData();
		
		for(int i = 0; i < bookmarks.length; i++)
		{
			System.out.println("Generating Bookmark for: " + fastest.get(i).getFormattedName());
			bookmarks[i] = createBookmark(fastest.get(i), wOffset, hOffset);
		}
	}
	
	private BufferedImage resizeHeader(double sFactor, double w, double h)
	{
		BufferedImage newImg = new BufferedImage(580, 480, header.getType());
		
		Graphics2D g = newImg.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g.drawImage(header, 0, 0, (int)(w * sFactor), (int)(h * sFactor), null);
		
		g.dispose();
		
		return newImg;
	}
	
	private BufferedImage createBookmark(Swimmer s, int wOffset, int hOffset)
	{
		BufferedImage bm = new BufferedImage(600, 1350, header.getType());
		
		//create the graphics objects
		Graphics2D g = bm.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 600, 1350);
		
		g.setColor(Color.BLACK);
		g.setBackground(Color.WHITE);
		
		//draw the border
		g.drawLine(1, 1, 599, 1);
		g.drawLine(1, 1, 1, 1349);
		g.drawLine(599, 1, 599, 1349);
		g.drawLine(1, 1349, 599, 1349);
		
		//draw the header image
		g.drawImage(header, wOffset, hOffset, header.getWidth(), header.getHeight(), null);
		
	/*------------- HEADER SECTION ---------------------------*/
		//update the font for this graphics context
		g.setFont(head);
		
		//get the name and header to draw
		ArrayList<String> headerLines = new ArrayList<String>();
		String name = s.getFormattedName();
		JLabel sizeLabel = new JLabel("name");
		int nWidth = sizeLabel.getFontMetrics(head).stringWidth(name);
		
		System.out.println("Header Width: " + nWidth);
		
		if(nWidth > 550)
		{
			System.out.println("Attempting to Shorten the string width");
			String[] names = name.split(" ");
			int i = 0;
			for(i = names.length; i > 0 && nWidth > 550; i--)
			{
				String temp = "";
				
				for(int j = 0; j <= i && j < names.length; j++)
				{
					temp += names[j] + " ";
				}
				
				System.out.println("Current String: " + temp);
				
				nWidth = sizeLabel.getFontMetrics(head).stringWidth(temp);
				
				System.out.println("String Width: " + nWidth);
			}
			
			//i++;
			
			String temp = names[0];
			
			for(int j = 1; j <= i; j++)
			{
				temp += names[j] + " "; 
			}
			
			headerLines.add(temp);
			
			String temp2 = "";
			
			for(int j = i + 1; j < names.length; j++)
			{
				temp2 += names[j];
			}
			
			if(!temp2.equals(""))
			{
				headerLines.add(temp2);
			}
		}
		else
		{
			headerLines.add(name);
		}
		
		nWidth = sizeLabel.getFontMetrics(head).stringWidth(headerText);
		
		if(nWidth > 550)
		{
			String[] names = headerText.split(" ");
			int i = 0;
			for(i = names.length; i > 0 && nWidth > 550; i--)
			{
				String temp = names[0];
				
				for(int j = 1; j <= i; j++)
				{
					temp += " " + names[j];
				}
				
				nWidth = sizeLabel.getFontMetrics(head).stringWidth(temp);
			}
			
			i++;
			
			String temp = "";
			
			for(int j = 0; j <= i; j++)
			{
				temp += names[j] + " "; 
			}
			
			headerLines.add(temp);
			
			String temp2 = "";
			
			for(int j = i + 1; j < names.length; j++)
			{
				temp2 += names[j];
			}
			
			if(!temp2.equals(""))
			{
				headerLines.add(temp2);
			}
		}
		else 
		{
			headerLines.add(headerText);
		}
		
		int height = sizeLabel.getFontMetrics(head).getHeight();
		
		int numLines = headerLines.size();
		
		int spacing = (numLines - 1) * LINE_SPACE;
		
		int totalHeadSize = spacing + (height * numLines);
		
		int remains = HEAD_SIZE - totalHeadSize;
		
		int h_offset = 520;
		
		if(remains > 0)
		{
			h_offset += remains / 2;
		}
		
		for(int j = 0; j < headerLines.size(); j++)
		{
			int vLoc = h_offset + (j * height) + (j * LINE_SPACE);
			int width = sizeLabel.getFontMetrics(head).stringWidth(headerLines.get(j));
			int hLoc = ((HEAD_WIDTH - width) / 2) + TEXT_MARGIN;
			g.drawString(headerLines.get(j), hLoc, vLoc);
		}
		
	/*----------------- Times Section --------------------*/
		//change the font
		g.setFont(body);
		
		ArrayList<String> bodyLines = new ArrayList<String>();
		
		if(!s.hasRecords() || s.getRecord(0).getEvent().equals("?"))
		{
			bodyLines.add("Participation!");
			
			int bHeight = sizeLabel.getFontMetrics(body).getHeight();
			int bWidth = sizeLabel.getFontMetrics(body).stringWidth(bodyLines.get(0));
			while(bWidth > BODY_WIDTH)
			{
				body = new Font(body.getFontName(), body.getStyle(), body.getSize() - 1);
				bWidth = sizeLabel.getFontMetrics(body).stringWidth(bodyLines.get(0));
			}
			
			bHeight = sizeLabel.getFontMetrics(body).getHeight();
			
			int bVLoc = ((BODY_HEIGHT - bHeight) / 2) + 750;
			int lineWidth = sizeLabel.getFontMetrics(body).stringWidth(bodyLines.get(0));
			int bHDif = BODY_WIDTH - lineWidth;
			int bHLoc = ((bHDif / 2) + BODY_MARGIN);
			
			g.drawString(bodyLines.get(0), bHLoc, bVLoc);
		}
		else
		{
			int bWidth = sizeLabel.getFontMetrics(body).stringWidth(s.getRecord(0).getEvent() + "\t" + s.getRecord(0).getTime());
			for(int j = 0; j < s.getNumRecords(); j++)
			{
				if(bWidth > BODY_WIDTH)
				{
					bodyLines.add(s.getRecord(j).getEvent());
					bodyLines.add(s.getRecord(j).getTime());
				}
				else
				{
					bodyLines.add(s.getRecord(j).getEvent() + " \t" + s.getRecord(j).getTime());
				}
			}
			
			int bHeight = sizeLabel.getFontMetrics(body).getHeight();
			
			int bSpacing = LINE_SPACE * (bodyLines.size() - 1);
			
			int bTHeight = (bHeight * bodyLines.size()) + bSpacing;
			
			int bVOffset = (BODY_HEIGHT - bTHeight) / 2;
			
			if(bVOffset < 0)
				bVOffset = 0;
			
			bVOffset += 750;
			
			for(int j = 0; j < bodyLines.size(); j++)
			{
				int lineWidth = sizeLabel.getFontMetrics(body).stringWidth(bodyLines.get(j));
				int bHDif = BODY_WIDTH - lineWidth;
				int bHLoc = ((bHDif / 2) + BODY_MARGIN);
				System.out.println("X Location: " + bHLoc + " | Line Width: " + lineWidth + " | Difference: " + bHDif);
				g.drawString(bodyLines.get(j), bHLoc, bVOffset + (bHeight * j) + (LINE_SPACE * j));
			}
		}
		//get rid of graphics object
		g.dispose();
		
		return bm;
	}

	public BufferedImage[] getBookmarks()
	{
		return bookmarks;
	}

	public boolean hasBookmarks()
	{
		if(bookmarks != null)
			return true;
		
		return false;
	}

	public BufferedImage[] setBookmarksIntoPages()
	{
		int numPages = bookmarks.length / 6;
		
		if(bookmarks.length % 6 != 0)
			numPages++;
		
		BufferedImage[] bPages = new BufferedImage[numPages];
		

		int j = 0;
		
		for(int i = 0; i < bPages.length; i++)
		{

			//create new context
			bPages[i] = new BufferedImage(PAGE_WIDTH, PAGE_HEIGHT, bookmarks[0].getType());
			Graphics2D g = bPages[i].createGraphics();
			
			for(int k = 0; k < 6 && k + j < bookmarks.length; k++)
			{
			
				int index = k + j;
				System.out.println("Adding Bookmark: " + index);
				
				if(k % 6 < 3)
				{
					g.drawImage(bookmarks[k + j], (600 * k) + (15 * k), 0, 600, 1350, null);
				}
				else
				{
					int hAlign = (k % 6) - 3;
					g.drawImage(bookmarks[k + j], (600 * hAlign) + (15 * hAlign), 1364, 600, 1350, null);
				}
			}
			
			g.dispose();
			j += 6;
		}
		
		return bPages;
	}

	public PDDocument createPDF()
	{
		BufferedImage[] pages = setBookmarksIntoPages();
		
		PDDocument doc = new PDDocument();
		
		for(int i = 0; i < pages.length; i++)
		{
			PDPage page = new PDPage();
			doc.addPage(page);
		}
		
		return doc;	
	}
	
	public void saveAsPngs(String fPath)
	{
		BufferedImage[] pages = setBookmarksIntoPages();
		
		String path = fPath.substring(0, fPath.length() - 4);
		
		for(int i = 0; i < pages.length; i++)
		{
			File outFile = new File(path + i + ".png");
			try {
				ImageIO.write(pages[i], "png", outFile);
			} catch (IOException e) {
				System.out.println("File Write Error");
			}
		}
	}
}
