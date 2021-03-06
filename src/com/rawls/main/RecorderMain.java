package com.rawls.main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;

import com.rawls.data.Record;
import com.rawls.data.Swimmer;
import com.rawls.gui.MainFrame;
import com.rawls.io.DataExporter;
import com.rawls.io.DataImporter;
import com.rawls.storage.SwimmerMasterList;

public class RecorderMain {
	
	public static final int OPEN_TEAM = 0;
	public static final int OPEN_ROSTER = 1;
	public static final int OPEN_NEW = 2;
	
	private static boolean ready = false;

	private static String vNum = "v0.3";
	
	private static MainFrame mf;
	
	private static String date = "01/01/1970";
	
	private static int[] datePieces = {1,1,1970};
	
	public static int open_method = OPEN_NEW;
	
	//Controls on where to save the team file
	public static boolean file_save_set = false;
	public static String file_path = "";
	
	public static void start() 
	{	
		File bPath;
		
		if(System.getProperty("os.name").toLowerCase().contains("mac"))
			bPath = new File("./Bookmarks");
		else if(System.getProperty("os.name").toLowerCase().contains("windows"))
			bPath = new File(".\\Bookmarks");
		else //linux
			bPath = new File("./Bookmarks");
		
		Path path = bPath.toPath();
		
		if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS))
		{
			bPath.mkdirs();
		}
		
		mf = new MainFrame("TimeRecorder");
		
		SwimmerMasterList.initList();
		
	}
	
	public static void changeToDatePanels(int open_step)
	{
		
		boolean loadStatus = false;
		
		//Check the mode by which we are going to open a team
		switch(open_step)
		{
		case OPEN_TEAM:
			loadStatus = openFromXML();
			break;
		case OPEN_ROSTER:
			loadStatus = openFromRoster();
			break;
		case OPEN_NEW:
			loadStatus = true;
			break;
		}
		
		if(loadStatus)
			mf.changeToDatePanel();
	}
	
	//Method to change the current date
	public static void changeDate(String d)
	{
		date = d;
		String[] parts = date.split("/");
		
		try{
			for(int i = 0; i < parts.length && i < datePieces.length; i++)
			{
				datePieces[i] = Integer.parseInt(parts[i]);
			}
		}catch(Exception e)
		{
			datePieces[0] = 1;
			datePieces[1] = 1;
			datePieces[2] = 1970;
		}
	}
	
	public static void changeToTeamView()
	{
		mf.switchToTeamPanel();
	}
	
	public static void updateSwimmerListPanel()
	{
		mf.updateSwimmerListPanel();
	}
	
	
	private static boolean openFromRoster()
	{
		JFileChooser jop = new JFileChooser();
		
		jop.setDialogTitle("Open From Roster");
		jop.setFileFilter(new FileNameExtensionFilter("Roster Files", "txt"));
		int result = jop.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			File rosterFile = jop.getSelectedFile();
			
			return DataImporter.importFromText(rosterFile);
		}
		else
		{
			return false;
		}
	}
	
	private static boolean openFromXML()
	{
		JFileChooser jop = new JFileChooser();
		
		jop.setDialogTitle("Open Existing Team");
		jop.setFileFilter(new FileNameExtensionFilter("TimeRecorderFiles", "xml"));
		
		int result = jop.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			File rosterFile = jop.getSelectedFile();
			
			try {
				file_path = rosterFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			boolean temp = DataImporter.importFromXML(rosterFile);
			
			if(temp)
				file_save_set = true;
			
			return temp;
		}
		else
		{
			return false;
		}
	}
	
	public static void switchToAddSwimmer()
	{
		mf.SwitchToAddSwimmer();
	}
	
	public static void viewSwimmer(Swimmer s)
	{
		mf.switchToSwimmer(s);
	}
	
	public static int[] getDate()
	{
		return datePieces;
	}
	
	public static void switchToChangeDate()
	{
		mf.switchToChangeDate();
	}
	
	
	
	
	
	/*----------------------------------------------------------------*/
	/*------------------------ OLD METHODS ---------------------------*/
	/*----------------------------------------------------------------*/
	
	/*
	public static void printStartingOptions()
	{
		System.out.println(startOptions);
	}
	
	public static void startingOptions()
	{
		while(!ready)
		{
			char opt = scan.next().charAt(0);
			switch(opt)
			{
			case '1':
				updateStatus("Loading From Roster\n");
				updateStatus("Please enter file with filepath:");
				String path = scan.next();
				File aFile = new File(path);
				DataImporter.importFromText(aFile);
				updateStatus("Please Enter the Header for the Roster data:");
				String head = compileHeader(scan);
				addHeadertoSwimmers(head);
				
				break;
			case '2':
				updateStatus("Loading from a saved database\n");
				updateStatus("Please enter file with filepath for database");
				String bpath = scan.next();
				File bFile = new File(bpath);
				DataImporter.importFromXML(bFile);
				updateStatus(SwimmerMasterList.getSwimmer(0).getHeader());
				defaultPath = bpath;
				break;
			case '?':
				printStartingOptions();
				break;
			case 'v':
				updateStatus("Current Version: " + vNum);
				break;
			case 'x':
				exitProtocolFromStart();
				break;
			}
		}
		
	}
	
	public static void getDateandFile()
	{
		while(defaultDate.equals("-11/-11/-1111"))
		{
			updateStatus("Please enter the day of meet/time trials (mm/dd/yyyy)");
			defaultDate = scan.next();
		}
		
		updateStatus("Please enter the filepath you would like to use for saving data:");
		givenPath = scan.next();
		
		printSwimmerList(SwimmerMasterList.getListDisplay());
		printMainOptions();
		mainOptions();
		
	}
	
	public static void exitProtocolFromStart()
	{
		updateStatus("\nAre you sure you would like to exit (y/n)");
		String s = scan.next();
		
		if(s.equalsIgnoreCase("y"))
		{
			System.exit(0);
		}
		else
		{
			printStartingOptions();
		}
		
	}
	
	public static void exitProtocolFromMain()
	{
		updateStatus("Are you sure you would like to exit (y/n)");
		
		String s = scan.next();
		
		if(s.equalsIgnoreCase("y"))
		{
			System.exit(0);
		}
		else
		{
			printMainOptions();
			mainOptions();
		}
	}
	
	public static void printMainOptions()
	{
		updateStatus(mainOptions);
	}
	
	public static void mainOptions()
	{
		while(select != 'x')
		{
			if(optionFlag < 0)
			{
			String in = scan.next();
			select = in.charAt(0);
			
			switch(select)
			{
			case '1':
				updateStatus("Adding a Swimmer\n");
				updateStatus("Enter their last name or 'x' to cancel");
				String lname = scan.next();
				if(lname.equals("x"))
				{
					updateStatus("Add a swimmer aborted!\n");
					printSwimmerList(SwimmerMasterList.getListDisplay());
					printMainOptions();
					break;
				}
				updateStatus("Enter their first name");
				String fname = scan.next();
				SwimmerMasterList.addSwimmer(new Swimmer(fname, lname));
				updateStatus("New swimmer list:\n");
				printSwimmerList(SwimmerMasterList.getListDisplay());
				printMainOptions();
				break;
			case '2':
				updateStatus("Editing a Swimmer\n");
				updateStatus("Enter Swimmer index number:");
				int index = -1;
				try
				{
					String swimE = scan.next();
					index = Integer.parseInt(swimE);
				}
				catch(Exception e)
				{
					updateStatus("Invalid Input Entered.");
					printSwimmerList(SwimmerMasterList.getListDisplay());
					printMainOptions();
					break;
				}
				
				printSwimmerDetails(SwimmerMasterList.getSwimmer(index));
				printSwimmerOptions();
				swimmerOptions(SwimmerMasterList.getSwimmer(index));
				break;
			case '3':
				updateStatus("Find and Edit Swimmer By Name\n");
				updateStatus("Enter the name you would like to find. (First Last)");
				String namein1;
				try
				{
					namein1 = scan.next();
				}
				catch(Exception e)
				{
					namein1 = "";
				}
				String namein2;
				try
				{
					namein2 = scan.next();
				}
				catch(Exception e)
				{
					namein2 = "";
				}
				String name = namein1 + " " + namein2;
				findSwimmerByName(name);
				break;
				case '4':
				updateStatus("Removing a Swimmer\n");
				updateStatus("Enter Swimmer index number:");
				String swimR = scan.next();
				
				try
				{
					int indexR = Integer.parseInt(swimR);
					removeSwimmer(indexR);
				}
				catch(Exception e)
				{
					updateStatus("Could not find specified swimmer\n");
				}
				updateStatus("\nUpdated Swimmer list:");
				printSwimmerList(SwimmerMasterList.getListDisplay());
				printMainOptions();
				break;
			case '5':
				updateStatus("Changing Date\n");
				updateStatus("Please enter the new date (MM/DD/YYYY)");
				String newDate = scan.next();
				defaultDate = newDate;
				updateStatus("Date set to " + defaultDate + "\n");
				printMainOptions();
				break;
			case '6':
				updateStatus("Saving to loaded Serial File\n");
				DataExporter.exportToXML(new File(givenPath));
				break;
			case '7':
				updateStatus("Saving to newly specified file\n");
				updateStatus("Please enter the full path for the new file:");
				String pathN = scan.next();
				File nFile = new File(pathN);
				DataExporter.exportToXML(nFile);
				printMainOptions();
				break;
			case '8':
				updateStatus("Exporting time sheets\n");
				updateStatus("Please enter the full path for time sheet file:");
				String pathT = scan.next();
				DataExporter.exportTimeSheet(new File(pathT));
				printMainOptions();
				break;
			case '9':
				updateStatus("Exporting Swimmer Details\n");
				updateStatus("Please Enter Swimmer Index Number");
				int swimIndex = -1;
				try
				{
					String swimindex = scan.next();
					swimIndex = Integer.parseInt(swimindex);
				}
				catch(Exception e)
				{
					updateStatus("Invalid input entered. Aborting Export!\n");
					printSwimmerList(SwimmerMasterList.getListDisplay());
					printMainOptions();
					swimIndex = 0;
					break;
				}
				updateStatus("Please Enter the full path for the time sheet file:");
				String pathS = scan.next();
				DataExporter.exportSwimmerDetails(new File(pathS), SwimmerMasterList.getSwimmer(swimIndex));
				printMainOptions();
				break;
			case '0':
				updateStatus("Exporting swimmers to XML\n");
				updateStatus("Please enter the full path for the XML file:");
				String pathB = scan.next();
				DataExporter.exportToXML(new File(pathB));
				updateStatus("Please enter the full path for the XSL file:");
				String pathX = scan.next();
				updateStatus("Please enter the full path for the PDF file:");
				String pathP = scan.next();
				DataExporter.renderPDF(new File(pathP), new File(pathX), new File(pathB));
				updateStatus("Success?");
				printMainOptions();
				break;
			case '?':
				printMainOptions();
				break;
			case 'h':
				updateStatus("File Type Help\n");
				updateStatus("When saving all data about swimmers for later use:\nUse '.ser' as the file type extension.\nWhen"
						+ " exporting data to either time sheets or bookmarks:\nUse '.txt' file type extension.\n"
						+ "\nInclude the specified extensions in the filepath whenever prompted.\n\n"
						+ "For more help email Connor at connor_rawls@tempe.gov");
				printMainOptions();
				break;
			case 'l':
				printSwimmerList(SwimmerMasterList.getListDisplay());
				break;
			case 'y':
				updateStatus("Setting Header\n");
				updateStatus("Please enter the header for the data");
				String head = compileHeader(scan);
				addHeadertoSwimmers(head);
				printMainOptions();
				break;
				
			}
			}
			else if(optionFlag == 1)
			{
				optionFlag = -1;
				
				updateStatus("Enter a swimmer's name (First Last)");
				
				String fname = scan.next();
				String lname = scan.next();
				String name = fname + " " + lname;
				
				findSwimmerByName(name);
			}
		}
		
		if(select == 'x')
		{
			exitProtocolFromMain();
		}
	}
	
	public static void printSwimmerDetails(Swimmer s)
	{
		updateStatus("\n" + s.getFormattedName() + ":\n");
		updateStatus("Records:");
		
		if(s.hasRecords())
		{
			for(int i = 0; i < s.getRecordList().size(); i++)
			{
				updateStatus(i + ": " + s.getRecordList().get(i));
			}
			updateStatus("");
		}
		else
		{
			updateStatus("No Records\n");
		}
	}
	
	public static void printSwimmerOptions()
	{
		updateStatus(swimmerOptions);
	}
	
	public static void swimmerOptions(Swimmer s)
	{
		while(select != 'c')
		{
			select = scan.next().charAt(0);
			switch(select)
			{
			case '1':
				updateStatus("Editing First Name\n");
				updateStatus("Please enter the new First Name");
				String nFName = scan.next();
				s.setFName(nFName);
				SwimmerMasterList.sort();
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
			case '2':
				updateStatus("Editing Last Name\n");
				updateStatus("Please enter the new last name");
				String nLName = scan.next();
				s.setLName(nLName);
				SwimmerMasterList.sort();
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
			case '3':
				updateStatus("Adding a record\n");
				updateStatus("Please Enter Swim Distance (1 - 25yds 2 - 50yds 3 - 100yds)");
				String ndist = scan.next();
				char Ndist = ndist.charAt(0);
				ndist = decideDistance(Ndist);
				updateStatus("Please enter the stroke (1 - Free 2 - Back 3 - Breast 4 - Fly 5 - I.M.)");
				String nstroke = scan.next();
				char snum = nstroke.charAt(0);
				nstroke = decideStroke(snum);
				updateStatus("Please enter the time (0:00.00)");
				String ntime = scan.next();
				String nevent = ndist + " " + nstroke;
				int[] ftime = separateTime(ntime);
				int[] fdate = separateDate(defaultDate);
				s.addRecord(new Record(nevent, ftime[0], ftime[1], ftime[2], fdate[1], fdate[0], fdate[2]));
				updateStatus("Record Created!\n");
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
			case '4':
				updateStatus("Editing a Record's Event\n");
				updateStatus("Please Enter Record Index Number");
				int rNum = 0;
				String rnum = scan.next();
				try
				{
					rNum = Integer.parseInt(rnum);
				}
				catch(Exception e)
				{
					updateStatus("Invalid input");
					printSwimmerOptions();
					break;
				}
				updateStatus("Please Enter Swim Distance (1 - 25yds 2 - 50yds 3 - 100yds)");
				String edist = "";
				try
				{
					edist = scan.next();
					char eDist = edist.charAt(0);
					edist = decideDistance(eDist);
				}
				catch(Exception e)
				{
					updateStatus("Invalid input");
					printSwimmerOptions();
					break;
				}
				updateStatus("Please enter the stroke (1 - Free 2 - Back 3 - Breast 4 - Fly 5 - I.M.)");
				String estroke = "";
				try
				{
					estroke = scan.next();
					char eStroke = estroke.charAt(0);
					estroke = decideStroke(eStroke);
				}
				catch(Exception e)
				{
					updateStatus("Invalid input");
					printSwimmerOptions();
					break;
				}
				String eevent = edist + " " + estroke;
				try
				{
					Record r1 = s.getRecord(rNum);
					r1.setEvent(eevent);
					updateStatus("Record Updated!\n");
					s.sortRecords();
				}
				catch(Exception e)
				{
					updateStatus("Record requested could not be found");
				}
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
			case '5':
				updateStatus("Editing a Record's Time\n");
				updateStatus("Please Enter Record Index Number");
				String recnum = scan.next();
				int recNum = Integer.parseInt(recnum);
				updateStatus("Please Enter New Time (0:00.00)");
				String etime = scan.next();
				int[] eftime = separateTime(etime);
				Record r2 = s.getRecord(recNum);
				r2.setTime(eftime[0], eftime[1], eftime[2]);
				updateStatus("Record Updated!\n");
				s.sortRecords();
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
			case '6':
				updateStatus("Editing a Record's Date\n");
				updateStatus("Please Enter Record Index Number");
				String recordn = scan.next();
				int recordN = Integer.parseInt(recordn);
				updateStatus("Please Enter New Date MM/DD/YYYY");
				String recd = scan.next();
				Record r3 = s.getRecord(recordN);
				int[] efdate = separateDate(recd);
				r3.setDate(efdate[1], efdate[0], efdate[2]);
				updateStatus("Record Updated!\n");
				s.sortRecords();
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
				
			case '7':
				updateStatus("Removing a Record\n");
				updateStatus("Enter index number of record to remove");
				String toremove = scan.next();
				
				try
				{
					s.removeRecord(Integer.parseInt(toremove));
					updateStatus("Record removed");
				}
				catch(Exception e)
				{
					updateStatus("Could not find specified record");
				}
				
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
				
			case '8':
				updateStatus("Editing age\n");
				updateStatus("Enter new age");
				String age = scan.next();
			
				try
				{
					s.setAge(Integer.parseInt(age));
					updateStatus("Age changed to " + age);
				}
				catch(Exception e)
				{
					updateStatus("Not a valid age");
				}
			
				printSwimmerDetails(s);
				printSwimmerOptions();
				break;
				
			case 'c':
				DataExporter.exportToXML(new File(givenPath));
				printSwimmerList(SwimmerMasterList.getListDisplay());
				printMainOptions();
				break;
			case 'v':
				optionFlag = 1;
				mainOptions();
				break;
			}
		}
	}
	
	public static int[] separateTime(String time)
	{
		int[] out = new int[3];
		
		int in1 = 0;
		int in2 = 0;
		
		for(int i = 0; i < time.length(); i++)
		{
			if(time.charAt(i) == ':' || time.charAt(i) == ';')
			{
				in1 = i;
				break;
			}
		}
		
		for(int i = in1 + 1; i < time.length(); i++)
		{
			if(time.charAt(i) == '.')
			{
				in2 = i;
				break;
			}
		}
		try
		{	
			out[0] = Integer.parseInt(time.substring(0, in1));
			out[1] = Integer.parseInt(time.substring(in1 + 1, in2));
			out[2] = Integer.parseInt(time.substring(in2 + 1));
		}
		catch(Exception e)
		{
			for(int i = 0; i < 3; i++)
			{
				out[i] = 00;
			}
		}
			
		
		return out;
	}
	
	public static int[] separateDate(String date)
	{
		int[] out = new int[3];
		
		int in1 = 0;
		int in2 = 0;
		
		for(int i = 0; i < date.length(); i++)
		{
			if(date.charAt(i) == '/')
			{
				in1 = i;
				break;
			}
		}
		
		for(int i = in1 + 1; i < date.length(); i++)
		{
			if(date.charAt(i) == '/')
			{
				in2 = i;
				break;
			}
		}
		
		try
		{
			out[0] = Integer.parseInt(date.substring(0, in1));
			out[1] = Integer.parseInt(date.substring(in1 + 1, in2));
			out[2] = Integer.parseInt(date.substring(in2 + 1));
		}
		catch(Exception e)
		{
			out[0] = 6;
			out[1] = 1;
			out[2] = 2014;
		}
		
		return out;
	}
	
	public static boolean isDate(String date)
	{
		int in1 = -1;
		int in2 = -1;
		
		for(int i = 0; i < date.length(); i++)
		{
			if(date.charAt(i) == '/')
			{
				in1 = i;
				break;
			}
		}
		
		for(int i = in1 + 1; i < date.length(); i++)
		{
			if(date.charAt(i) == '/')
			{
				in2 = i;
				break;
			}
		}
		
		if(in1 == -1 || in2 == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static void printSwimmerList(Vector<String> list)
	{
		updateStatus("\nSwimmers: \n");
		
		for(int i = 0; i < list.size(); i++)
		{
			updateStatus(i + ": " + list.get(i));
		}
		
		updateStatus("\n");
	}
	
	
	public static void removeSwimmer(int index)
	{
		SwimmerMasterList.removeSwimmer(index);
		
	}
	
	
	
	
	public static void addRecord(Swimmer s)
	{	
		Record in = null;
		
		s.addRecord(in);
		
		
	}
	
	
	public static void findSwimmerByName(String name)
	{
		String[] list = SwimmerMasterList.getFormattedNames();
		
		int result = -1;
		
		for(int i = 0; i < list.length; i++)
		{
			if(name.equalsIgnoreCase(list[i]))
			{
				result = i;
				break;
			}
		}
		
		if(result != -1)
		{
			printSwimmerDetails(SwimmerMasterList.getSwimmer(result));
			printSwimmerOptions();
			swimmerOptions(SwimmerMasterList.getSwimmer(result));
		}
		else
		{
			updateStatus("Could not find swimmer named " + name + ".");
			printMainOptions();
		}
	}
	
	private static String decideStroke(char strokeNum)
	{
		switch(strokeNum)
		{
		case '1':
			return "Freestyle";
		case '2':
			return "Backstroke";
		case '3':
			return "Breaststroke";
		case '4':
			return "Butterfly";
		case '5':
			return "I.M.";
		}
		
		return "Undefined";
	}
	
	private static String decideDistance(char distNum)
	{
		switch(distNum)
		{
		case '1':
			return "25";
		case '2':
			return "50";
		case '3':
			return "100";
		}
		
		return "Undefined";
	}
	
	public static void updateRecord(Record r)
	{
		//r.setDate(vrPane.getDay(), vrPane.getMonth(), vrPane.getYear());
		//r.setEvent(vrPane.getEvent());
		//r.setTime(vrPane.getMins(), vrPane.getSec(), vrPane.getMili()); 
		
	}
	*/
	public static void updateStatus(String status)
	{
		//System.out.println(status);
	}
	/*
	public static void exportTimes(File aF)
	{
		SwimmerMasterList.sort();
		DataExporter.exportTimeSheet(aF);
	}
	
	public static void exportBookmarks(File aF) {
		DataExporter.exportBookMarkXML(aF);
	}
	
	public static void addHeadertoSwimmers(String header) {
		ArrayList<Swimmer> swimmers = SwimmerMasterList.getList();
		
		for(int i = 0; i < swimmers.size(); i++)
		{
			swimmers.get(i).setHeader(header);
		}
	}
	
	public static String compileHeader(Scanner s) {
		String result = "Team Tempe Tigersharks 2014";
		return result;
	}
	
	*/
}