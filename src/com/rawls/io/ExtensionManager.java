package com.rawls.io;

import java.io.File;

public class ExtensionManager {

	/*
	 * function to get a path for a file with the correct file extension appended
	 * normally used when saving a file to disk
	 */
	public static String getConformedPath(String inputPath, String extension) {
		
		//if the path is too short to contain the extension
		if(inputPath.length() < extension.length())
		{
			return inputPath + extension;
		}
		else
		{
			String possibleExtension = inputPath.substring(inputPath.length() - 4, inputPath.length());
			
			switch(possibleExtension) {
			case ".png":
				swapExtension(inputPath, extension);
				break;
			case ".jpg":
				swapExtension(inputPath, extension);
				break;
			case ".bmp":
				swapExtension(inputPath, extension);
				break;
			case ".doc":
				swapExtension(inputPath, extension);
				break;
			case ".pdf":
				swapExtension(inputPath, extension);
				break;
			case ".xml":
				swapExtension(inputPath, extension);
				break;
			case ".mov":
				swapExtension(inputPath, extension);
				break;
			case ".wav":
				swapExtension(inputPath, extension);
				break;
			case ".tif":
				swapExtension(inputPath, extension);
				break;
			case ".ppt":
				swapExtension(inputPath, extension);
				break;
			case ".zip":
				swapExtension(inputPath, extension);
				break;
			case ".csv":
				swapExtension(inputPath, extension);
				break;
			default:
				inputPath += extension;
				break;
				
			}
			
			return inputPath;
		}
		
	}
	
	/*
	 * helper function to getConformedPath()
	 */
	private static String swapExtension(String input, String ext)
	{
		input = input.substring(0, input.length() - 4);
		input += ext;
		
		return input;
	}
	
	/*
	 * returns the folder which the specified filepath is 
	 * pointing to
	 */
	public static String getEnclosingDirectoryPath(String inputPath)
	{
		//shave the string a character at a time until a slash is found
		while(inputPath.length() > 1 && inputPath.charAt(inputPath.length() - 1) != File.separatorChar)
		{
			inputPath = inputPath.substring(0, inputPath.length() - 1);
		}
		
		if(inputPath.length() == 1)
		{
			return "";
		}
		else if(inputPath.length() > 1)
		{
			return inputPath.substring(0, inputPath.length() - 1);
		}
		else
		{
			return "";
		}
	}
	
}
