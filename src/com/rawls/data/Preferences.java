package com.rawls.data;

import java.io.File;

public class Preferences {
	
	public Preferences()
	{
		
	}
	
	public void savePreferences()
	{
		String os = System.getProperty("os.name");
		
		if(os.contains("Mac"))
		{
			String location = System.getProperty("user.home") + "/Library/Application Support/TimeRecorder/";
			String file = location + "prefs.txt";
			
			File dir = new File(location);
			File prefFile = new File(file);
			
		}
		
		else if(os.toLowerCase().contains("windows"))
		{
			String location = System.getProperty("user.home") + "\\TimeRecorder\\";
			String file = location + "prefs.txt";
			
			File dir = new File(location);
			File prefFile = new File(file);
		}
	}

	/**
	 * 
	 * @author CRawls
	 *
	 *Individual Element within a group of preferences.
	 *Consists of a name and an integer value
	 */
	public class PrefElement{
		
	}
	
	/**
	 * 
	 * @author CRawls
	 *
	 *Container for groups of Prefelements.
	 *Consists of a name and an array of PrefElements
	 */
	public class PrefGroup{
		
	}
}
