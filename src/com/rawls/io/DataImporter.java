package com.rawls.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.rawls.data.Record;
import com.rawls.data.Swimmer;
import com.rawls.main.RecorderMain;
import com.rawls.storage.SwimmerMasterList;
import com.thoughtworks.xstream.XStream;

public class DataImporter {
	
	private static final String CSV_DATE = "date"; //used in csv parsing to find the date line
	private static final String[] CSV_LENGTHS = {"25", "50", "100", "200"};
	private static final String[] CSV_STROKES = {"Freestyle", "free", "Backstroke", "back", "Breaststroke", "breast", "Butterfly","fly","IM"}; 
	
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
				int age = 6;
				
				String[] parts = in.split("[|]");
				
				for(int i = 0; i < parts[0].length(); i++)
				{
					if(in.charAt(i) == ',')
					{
						last = parts[0].substring(0, i);
						first = parts[0].substring(i + 1);
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
		
		SwimmerMasterList.sort();
		return true;
		
	}
	
	
	/*
	 * Method for importing swimmer times in bulk from a csv document
	 * Will change the date and register new swimmers if needed
	 * Any times after a new date will get the changed date, prior will get previous working date
	 */
	public static void importCSVData(File csvFile)
	{
		//just realized I left c syntax here, oops
		int[] date = RecorderMain.getDate();
		
		Scanner scan = null;
		
		try {
			//open a scanner to read the file
			scan = new Scanner(csvFile);
			
			int lineNum = 0;
			
			//iterate through the contents 
			while(scan.hasNextLine()) {
				
				String line = scan.nextLine();
				
				//split the line on the comma
				String[] contents = line.split(",");
				
				//check if this is a date line first
				if(contents[0].equalsIgnoreCase(CSV_DATE))
				{
					//if so, attempt to change the date
					try {
						int newDate[] = new int[3];
						//this block is most likely to produce an error
						newDate[0] = Integer.parseInt(contents[1]);
						newDate[1] = Integer.parseInt(contents[2]);
						newDate[2] = Integer.parseInt(contents[3]);
						
						//if we get here, we should be safe
						date = newDate.clone();
					} catch (Exception e) /* really just catching someone entering a letter or too few numbers */{
						System.err.println("Malformed Date Line!");
						e.printStackTrace();
					}
				}
				else if(contents.length == 7)
				{
					String fname = contents[0];
					String lname = contents[1];
					
					String testname = fname + " " + lname;
					String testname2 = lname + " " + fname; //try inverting in the case the name was entered backwards
					
					
					Swimmer s = SwimmerMasterList.getSwimmer(testname);
					
					
					//check if we were returned a default constructed swimmer object
					if(!s.getFormattedName().equalsIgnoreCase(testname))
					{
						s = SwimmerMasterList.getSwimmer(testname2);
					}
					
					//if we still didn't find a swimmer, then take extreme measures
					if(!s.getFormattedName().equalsIgnoreCase(testname2) && !s.getFormattedName().equalsIgnoreCase(testname))
					{
						s = getMysterySwimmer(fname, lname);
					}
					
					//check the swim length
					String length = contents[2];
					
					boolean lengthFound = false;
					
					//make the string standard
					for(int i = 0; i < CSV_LENGTHS.length && !lengthFound; i++)
					{
						if(length.equalsIgnoreCase(CSV_LENGTHS[i]))
						{
							length = CSV_LENGTHS[i];
							lengthFound = true;
						}
					}
					
					if(!lengthFound)
						length = "25";
					
					//check the stroke
					String stroke = contents[3];
					
					boolean strokeFound = false;
					
					for(int i = 0; i < CSV_STROKES.length && !strokeFound; i++)
					{
						if(stroke.equalsIgnoreCase(CSV_STROKES[i]))
						{
							if(i % 2 == 1)
								stroke = CSV_STROKES[i-1];
							else
								stroke = CSV_STROKES[i];
							
							strokeFound = true;
						}
					}
					
					if(!strokeFound)
						stroke = CSV_STROKES[0];
					
					String event = length + " " + stroke;
					
					int[] time = new int[3];
					
					boolean goodTime = true;
					
					try {
						time[0] = Integer.parseInt(contents[4]);
						time[1] = Integer.parseInt(contents[5]);
						time[2] = Integer.parseInt(contents[6]);
					} catch (Exception e) {
						goodTime = false;
						System.err.println("Malformed Time Entry");
						e.printStackTrace();
					}
					
					if(goodTime)
						s.addRecord(new Record(event, time[0],time[1],time[2],date[0],date[1],date[2]));
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Error on line: " + lineNum + "Not enough arguments!");
				}
				
				lineNum++;
			}
			
		} catch (FileNotFoundException e) {
			System.err.print("Error Reading CSV File!");
			e.printStackTrace();
		} finally {
			scan.close();
		}
	}

	
	private static Swimmer getMysterySwimmer(String fname, String lname)
	{
		String testname = fname + " " + lname;
		
		Swimmer s = new Swimmer();
		
		int response = JOptionPane.showConfirmDialog(null, "No Swimmer found with name " + testname + ". Create a new swimmer?", "A Wild Swimmer Appears", JOptionPane.YES_NO_OPTION);
		
		//if yes is chosen, create and add the swimmer to the list
		if(response == JOptionPane.YES_OPTION)
		{
			s = new Swimmer(fname, lname,6);
			SwimmerMasterList.addSwimmer(s);
			return s;
		}
		else
		{
			Vector<String> sList = SwimmerMasterList.getListDisplay();
			String[] nList = new String[sList.size()];
			sList.copyInto(nList);
			
			String sResult = (String) JOptionPane.showInputDialog(null, "Select a swimmer to assign time to", "Choose", JOptionPane.QUESTION_MESSAGE, null, nList, nList[0]);
			
			for(int i = 0; i < nList.length && sList != null; i++)
			{
				if(sResult.equals(nList[i]))
				{
					s = SwimmerMasterList.getSwimmer(i);
					return s;
				}
			}
		}
		
		return s;
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
