package com.rawls.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AboutPanel extends JPanel{

	
	public AboutPanel()
	{
		Dimension d = WindowReference.getDimension();
		Dimension curr = new Dimension(d.width / 2, d.height);
		
		this.setLayout(null);
		this.setPreferredSize(curr);
		
		String path;
		
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			path = ".\\images\\logo.png";
		}
		else
		{
			path = "./images/logo.png";
		}
		
		File imgfile = new File(path);
		
		try {
			BufferedImage bi = ImageIO.read(imgfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private ImageIcon scaleLogo(BufferedImage bi, int[] scale)
	{	
		BufferedImage bf = new BufferedImage(scale[0], scale[1], bi.getType());
		
		Graphics2D g = bf.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g.drawImage(bi, 0, 0, scale[0], scale[1], null);
		
		g.dispose();
		
		return new ImageIcon(bf);
	}
}
