package com.rawls.gui;

import java.awt.Dimension;

public class WindowReference {

	private static double[] MOD_LARGE = {1.0,1.0};
	private static double[] MOD_MEDIUM = {0.75, 0.7467};
	private static double[] MOD_SMALL = {0.5, 0.5};
	
	public static final int WINDOW_LARGE = 0;
	public static final int WINDOW_MEDIUM = 1;
	public static final int WINDOW_SMALL = 2;
	
	public static final Dimension SIZE_LARGE = new Dimension(1500,750);
	public static final Dimension SIZE_MEDIUM = new Dimension(1125, 560);
	public static final Dimension SIZE_SMALL = new Dimension(750, 375);
	
	private static int windowSize = WINDOW_MEDIUM;
	
	
	public static Dimension getDimension()
	{
		switch(windowSize)
		{
		case WINDOW_LARGE:
			return SIZE_LARGE;
		case WINDOW_MEDIUM:
			return SIZE_MEDIUM;
		case WINDOW_SMALL:
			return SIZE_SMALL;
			
		}
		
		return SIZE_MEDIUM;
	}

		
	public static int[] getNewDims(int x, int y)
	{
		int[] output = new int[2];
			
		switch(windowSize)
		{
		case WINDOW_LARGE:
			output[0] = (int) Math.round((x * MOD_LARGE[0]));
			output[1] = (int) Math.round((y * MOD_LARGE[1]));
			break;
		case WINDOW_MEDIUM:
			output[0] = (int) Math.round((x * MOD_MEDIUM[0]));
			output[1] = (int) Math.round((y * MOD_MEDIUM[1]));
			break;
		case WINDOW_SMALL:
			output[0] = (int) Math.round((x * MOD_SMALL[0]));
			output[1] = (int) Math.round((y * MOD_SMALL[1]));
			break;
		}
			
		return output;
	}

}
