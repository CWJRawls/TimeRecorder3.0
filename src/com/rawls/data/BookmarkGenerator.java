package com.rawls.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.rawls.io.ExtensionManager;
import com.rawls.storage.SwimmerMasterList;

public class BookmarkGenerator {


	
	//constants for placing bookmarks into pages
	private static final int PAGE_WIDTH = 2400;//1845;
	private static final int PAGE_HEIGHT = 3150;//2715;
	private static final int PAGE_SPACE = 15;
	private static final int SET_HORIZ = 3;
	private static final int SET_VERT = 2;
	
	//Constants for bookmark generation
	private static final int TOTAL_WIDTH = (PAGE_WIDTH - ((SET_HORIZ - 1) * PAGE_SPACE)) / SET_HORIZ; //get the even division width wise
	private static final int TOTAL_HEIGHT = (PAGE_HEIGHT - (PAGE_SPACE * (SET_VERT - 1))) / SET_VERT; //divide up the height
	private static final int HEADER_IMG_HOFFSET = 10;
	private static final int HEADER_IMG_VOFFSET = 10;
	private static final int HEADER_IMG_WIDTH = TOTAL_WIDTH - (HEADER_IMG_HOFFSET * 2);
	private static final int HEADER_IMG_HEIGHT = (int)(((float)TOTAL_HEIGHT) * 0.356); //header image should accout for 35.6% of vertical space on bookmark
	private static final int LINE_SPACE = 5; //space between individual lines
	private static final int TEXT_MARGIN = 25; //space between the text and the vertical edges
	private static final int HEAD_SIZE = (int)(((float)TOTAL_HEIGHT) * 0.185); //text portion of header should take 18.5% of vertical space
	private static final int HEAD_WIDTH = TOTAL_WIDTH - (2 * TEXT_MARGIN); //width available for drawing text
	private static final int BODY_MARGIN = 10; //spacing in pixels from the vertical edge for body text
	private static final int BODY_WIDTH = TOTAL_WIDTH - (BODY_MARGIN * 2); //width available for drawing text in body section
	private static final int BODY_HEIGHT = (int)(((float)TOTAL_HEIGHT) * 0.444);//the body of the bookmark should account for 44.4% of vertical space //original setting:600
	
	private Font head, body;
	private BufferedImage header;
	private BufferedImage[] bookmarks = null;
	private String headerText;
	
	/*
	 * Constructor
	 * takes the font objects for the header and the body of the bookmarks
	 * also requires the logo image and the string to be placed in the header
	 */
	public BookmarkGenerator(Font head, Font body, BufferedImage header, String hText)
	{
		this.head = head;
		this.body = body;
		this.header = header;
		headerText = hText;
	}
	
	/*
	 * creates and stores an array of bufferedimages representing bookmarks
	 * these are not paged yet, just an array of images
	 * just creates the settings and then instructs the code to create each image
	 */
	public void generateBookmarks()
	{
		//check the size of the header image
		int w = header.getWidth();
		int h = header.getHeight();
		
		//resize the header if needed (image is too large)
		if(w > HEADER_IMG_WIDTH || h > HEADER_IMG_HEIGHT)
		{	
			if(w > HEADER_IMG_WIDTH && (h <= HEADER_IMG_HEIGHT || h <= w)) //if the width is the largest dimension
			{
				double sFactor = ((double)HEADER_IMG_WIDTH) / ((double)w);
				header = resizeHeader(sFactor, w, h);
			}
			else if(h > HEADER_IMG_HEIGHT && (w <= HEADER_IMG_WIDTH || w <= h)) //if the height is the largest dimension
			{
				double sFactor = ((double)HEADER_IMG_HEIGHT) / (double)h;
				header = resizeHeader(sFactor, w, h);
			}
		}
		
		//check the new size of the header image
		w = header.getWidth();
		h = header.getHeight();
		
		//get the left & top spacing for when we draw the header onto the bookmark
		int wOffset = ((HEADER_IMG_WIDTH - w) / 2) + HEADER_IMG_HOFFSET;
		int hOffset = ((HEADER_IMG_HEIGHT - h) / 2) + HEADER_IMG_VOFFSET;
		
		bookmarks = new BufferedImage[SwimmerMasterList.getList().size()]; //create the array for the number of swimmers on the list
		
		ArrayList<Swimmer> fastest = SwimmerMasterList.getFastestData(); //get the fastest times if there are any for each swimmer
		
		for(int i = 0; i < bookmarks.length; i++) //for each swimmer generate a bookmark
		{
			System.out.println("Generating Bookmark for: " + fastest.get(i).getFormattedName());
			bookmarks[i] = createBookmark(fastest.get(i), wOffset, hOffset);
		}
	}
	
	/*
	 * For use when the image provided for the header logo is too large
	 * resizes the image down to the correct size for the print
	 * recursive to ensure that all sides fit inside the new image bounds
	 */
	private BufferedImage resizeHeader(double sFactor, double w, double h)
	{
		
		int newWidth = (int)(w * sFactor);
		int newHeight = (int)(h * sFactor);
		
		if(newWidth > HEADER_IMG_WIDTH || newHeight > HEADER_IMG_HEIGHT){
			if(newWidth > HEADER_IMG_WIDTH)
			{
				return resizeHeader(((double)HEADER_IMG_WIDTH) / ((double)newWidth), newWidth, newHeight);
			}
			else
			{
				return resizeHeader(((double)HEADER_IMG_HEIGHT) / ((double)newHeight), newWidth, newHeight);
			}
		}
		else {
			
			BufferedImage newImg = new BufferedImage(HEADER_IMG_WIDTH, HEADER_IMG_HEIGHT, header.getType());
		
			System.out.println(HEADER_IMG_WIDTH + " x " + HEADER_IMG_HEIGHT);
	
			Graphics2D g = newImg.createGraphics();
		
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
			System.out.println("Scaling Factor: " + sFactor + " | w: " + w + " | h: " + h);
		
			g.drawImage(header, 0, 0, (int)(w * sFactor), (int)(h * sFactor), null);
		
			g.dispose();
		
			return newImg;
		}
	}
	
	/*
	 * Function to actually create each individual bookmark image
	 */
	private BufferedImage createBookmark(Swimmer s, int wOffset, int hOffset)
	{
		BufferedImage bm = new BufferedImage(TOTAL_WIDTH, TOTAL_HEIGHT, header.getType());
		
		//create the graphics objects
		Graphics2D g = bm.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
		
		g.setColor(Color.BLACK);
		g.setBackground(Color.WHITE);
		
		//draw the border
		g.drawLine(1, 1, TOTAL_WIDTH - 1, 1);
		g.drawLine(1, 1, 1, TOTAL_HEIGHT - 1);
		g.drawLine(TOTAL_WIDTH - 1, 1, TOTAL_WIDTH - 1, TOTAL_HEIGHT - 1);
		g.drawLine(1, TOTAL_HEIGHT - 1, TOTAL_WIDTH - 1, TOTAL_HEIGHT - 1);
		
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
		
		if(nWidth > HEAD_WIDTH)
		{
			System.out.println("Attempting to Shorten the string width");
			String[] names = name.split(" ");
			int i = 0;
			for(i = names.length; i > 0 && nWidth > HEAD_WIDTH; i--)
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
		
		if(nWidth > HEAD_WIDTH)
		{
			String[] names = headerText.split(" ");
			int i = 0;
			for(i = names.length; i > 0 && nWidth > HEAD_WIDTH; i--)
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
		
		int h_offset = HEADER_IMG_HEIGHT + (2 * HEADER_IMG_VOFFSET);
		
		if(remains > 0)
		{
			h_offset += remains / 2;
		}
		
		for(int j = 0; j < headerLines.size(); j++)
		{
			int vLoc = h_offset + (j * height) + (j * LINE_SPACE);
			int width = sizeLabel.getFontMetrics(head).stringWidth(headerLines.get(j));
			int hLoc = ((HEAD_WIDTH - width) / 2) + TEXT_MARGIN;
			System.out.println("Header Text X Position: "+ hLoc + " | Line Width: " + width + " | Header Width: " + HEAD_WIDTH);
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
			
			int bVLoc = ((BODY_HEIGHT - bHeight) / 2) + (HEADER_IMG_HEIGHT + (3 * HEADER_IMG_VOFFSET) + HEAD_SIZE);
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
			
			bVOffset += (HEADER_IMG_HEIGHT + (3 * HEADER_IMG_VOFFSET) + HEAD_SIZE);
			
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

	/*
	 * returns the stored array of images from the object
	 */
	public BufferedImage[] getBookmarks()
	{
		return bookmarks;
	}

	/*
	 * Should be called before getBookmarks()
	 * checks if any bookmarks have been made yet
	 */
	public boolean hasBookmarks()
	{
		if(bookmarks != null)
			return true;
		
		return false;
	}

	/*
	 * Takes the stored array of bookmark images and arranges them into larger page sized images
	 */
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
					g.drawImage(bookmarks[k + j], (TOTAL_WIDTH * k) + (PAGE_SPACE * k), 0, TOTAL_WIDTH, TOTAL_HEIGHT, null);
				}
				else
				{
					int hAlign = (k % 6) - 3;
					g.drawImage(bookmarks[k + j], (TOTAL_WIDTH * hAlign) + (PAGE_SPACE * hAlign), (TOTAL_HEIGHT - 1), TOTAL_WIDTH, TOTAL_HEIGHT, null);
				}
			}
			
			g.dispose();
			j += 6;
		}
		
		return bPages;
	}

	public void createPDF(String fPath)
	{
		BufferedImage[] pages = setBookmarksIntoPages();
		
		PDDocument doc = new PDDocument();
		
		for(int i = 0; i < pages.length; i++)
		{
			PDPage page = new PDPage(); //create a new 8.5 x 11in page
			doc.addPage(page); //add it to the document
			
			PDRectangle pageSize = page.getMediaBox(); //get the rectangle that bounds the drawable area
			
			//get the size of the drawable area on the page
			float w = pageSize.getWidth();
			float h = pageSize.getHeight();
			
			try {
				PDPageContentStream stream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true);
				
				PDImageXObject image = LosslessFactory.createFromImage(doc, pages[i]);
				
				//get how much to shrink the image for it to fit the page
				float hscale = (w - 40) / image.getWidth();
				float vscale = (h - 40) / image.getHeight();
				
				stream.drawImage(image, 20, 20, image.getWidth() * hscale, image.getHeight() * vscale);
				stream.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Error on page: " + i);
				e.printStackTrace();
			}
			
		}
		
		String path = ExtensionManager.getConformedPath(fPath, ".pdf");
		
		File outputFile = new File(path);
		File outputFolder = new File(ExtensionManager.getEnclosingDirectoryPath(path));
		outputFolder.mkdirs(); //just make sure that everything exists in the specified path
		
		try {
			doc.save(outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("PDF Save Failed!");
			e.printStackTrace();
		}
		
		try {
			doc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void saveAsPngs(String fPath)
	{	
		BufferedImage[] pages = setBookmarksIntoPages();
		
		String path = fPath.substring(0, fPath.length() - 4);
		
		for(int i = 0; i < pages.length; i++)
		{
			File outFile = new File(path + i + ".png");
			try {
				outFile.mkdirs();
				ImageIO.write(pages[i], "png", outFile);
			} catch (IOException e) {
				System.out.println("File Write Error");
			}
		}
	}
}
