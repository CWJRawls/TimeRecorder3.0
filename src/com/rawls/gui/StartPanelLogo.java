package com.rawls.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanelLogo extends JPanel implements TRComponent{
	
	private BufferedImage logo = null;
	private int[] oScale = {600, 600};
	
	public StartPanelLogo()
	{
		this.setLayout(null);
		Dimension curr = (Dimension) WindowReference.getDimension().clone();
		
		System.out.println("Dimension: " + curr.width + "x" + curr.height);
		
		this.setPreferredSize(new Dimension(curr.width / 2, curr.height));
		
		curr.setSize(curr.getWidth() / 2, curr.getHeight());
		
		System.out.println("Width: " + curr.getWidth() + " Height: " + curr.getHeight());
		
		try {
			if(System.getProperty("os.name").toLowerCase().contains("mac"))
				logo = ImageIO.read(new File("./images/TimeRecorderLogo.jpg"));
			else
				logo = ImageIO.read(new File(".\\images\\TimeRecorderLogo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] iLoc = {(int)(curr.width * 0.05), (int)(curr.width * 0.05)};
		int[] scale = {(int)(curr.width * 0.9), (int) (curr.width * 0.9)};
		
		System.out.println("X: " + iLoc[0] + " Y: " + iLoc[1]);
		System.out.println("X-Dim: " + scale[0] + " Y-Dim: " + scale[1]);
		
		ImageIcon sLogo = scaleLogo(logo, scale);
		
		JLabel icon = new JLabel(sLogo);
		
		icon.setBounds(iLoc[0], iLoc[1], scale[0], scale[1]);
		
		this.add(icon);
		
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

	@Override
	public void keyTyped(int kCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int kCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int kCode) {
		// TODO Auto-generated method stub
		
	}
	

}
