package com.rawls.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.rawls.data.Record;
import com.rawls.data.Swimmer;
import com.rawls.main.RecorderMain;
import com.rawls.storage.SwimmerMasterList;
import com.thoughtworks.xstream.XStream;

public class DataImporter {
	
	private static XStream xstream = new XStream();
	
	public static void completeMapping()
	{
		xstream.alias("swimmer", Swimmer.class);
		xstream.alias("record", Record.class);
	}
	
	//Uses objectinputstream to handle export method
	public static boolean importFromXML(File xmlFile)
	{
		ObjectInputStream ois;
		try {
			ois = xstream.createObjectInputStream(new FileInputStream(xmlFile));
			
			ArrayList<Swimmer> temp;
			temp = (ArrayList<Swimmer>) ois.readObject();
			
			ois.close();
			
			for(int i = 0; i < temp.size(); i++)
			{
				
				//Clear any default records placed during export
				if(temp.get(i).getRecord(0).getEvent().equals("?"))
				{
					temp.get(i).removeRecord(0);
				}
				
				SwimmerMasterList.addSwimmer(temp.get(i));
		
			}
		}
		 catch (Exception e) {
		
			JOptionPane.showMessageDialog(null, "Error Opening File!");
			return false;
			
		}
		
		return true;
		
		
	}
	
	public static boolean importFromText(File textFile)
	{
		try {
			Scanner scan = new Scanner(textFile);
			
			while(scan.hasNext())
			{
				String in = scan.nextLine();
				System.out.println(in);
				String first = "";
				String last = "";
				int age = 99;
				
				String[] parts = in.split("[|]");
				
				for(int i = 0; i < parts[0].length(); i++)
				{
					if(in.charAt(i) == ',')
					{
						last = parts[0].substring(0, i);
						first = parts[0].substring(i + 2);
					}
				}
				
				if(parts.length > 1)
				{
					age = Integer.parseInt(parts[1]);
				}
				
				SwimmerMasterList.addSwimmer(new Swimmer(first, last, age));
				RecorderMain.updateStatus("Data loaded!\n");
				
			}
			
			scan.close();
			
		} catch (FileNotFoundException e) {
			//RecorderMain.updateStatus("Could not open file, data not loaded!");
			//RecorderMain.updateStatus("Data not loaded!\n");
			//RecorderMain.printStartingOptions();
			//RecorderMain.startingOptions();
			
			JOptionPane.showMessageDialog(null, "Error, Could Not Read File!");
			return false;
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error, Could Not Read File!");
			return false;
		}
		
		return true;
		
		//RecorderMain.getDateandFile();
		
		
		
	}

	/* !!!!! This Method is Legacy and is no Longer Called !!!!! */
	public static void importFromSerial(File serialFile)
	{
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serialFile));
			
			ArrayList<Swimmer> tempList = (ArrayList<Swimmer>) ois.readObject();
			
			for(int i = 0; i < tempList.size(); i++)
			{
				SwimmerMasterList.addSwimmer(tempList.get(i));
			}
			
			ois.close();
			
			RecorderMain.updateStatus("Data loaded!");
			//RecorderMain.getDateandFile();
			
			
		} catch (FileNotFoundException e) {
			
			RecorderMain.updateStatus("File not found!\n");
			//RecorderMain.printStartingOptions();
			
		} catch (IOException e) {
			
			RecorderMain.updateStatus("Could not load file!\n");
			//RecorderMain.printStartingOptions();
			
		} catch (ClassNotFoundException e) {
			
			RecorderMain.updateStatus("Error reading data, file not loaded!\n");
			//RecorderMain.printStartingOptions();
			
		}
	}
}
