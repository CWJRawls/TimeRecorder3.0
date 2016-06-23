package com.rawls.data;

import java.awt.Color;
import java.util.ArrayList;

public class ChartElement {

	private Color c; //color of the line for the plot
	private ArrayList<Integer> data; //floating point representation of the times in seconds
	private String[] times; //string representation of the average time
	private ArrayList<Date> dates; //date objects representing the days on which data was logged
	private String name;
	
	public ChartElement(String n, Color c)
	{
		name = n;
		this.c = c;
	}
	
	public void addData(int time, int d, int m, int y)
	{
		
	}
	
	
	class Date {
		private int day, month, year;
		
		public Date(int d, int m, int y)
		{
			day = d;
			month = m;
			year = y;
		}
		
		public int getYear()
		{
			return year;
		}
		
		public int getMonth()
		{
			return month;
		}
		
		public int getDay()
		{
			return day;
		}
		
		public int compare(Date d)
		{
			if(year > d.getYear())
			{
				return -1;
			}
			else if(year < d.getYear())
			{
				return 1;
			}
			else
			{
				if(month > d.getMonth())
				{
					return -1;
				}
				else if(month < d.getMonth())
				{
					return 1;
				}
				else
				{
					if(day > d.getDay())
					{
						return -1;
					}
					else if(day < d.getDay())
					{
						return 1;
					}
					else
					{
						return 0;
					}
				}
			}
		}
	}
}
