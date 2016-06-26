package com.rawls.data;

import java.awt.Color;
import java.util.ArrayList;

public class ChartElement {

	private Color c; //color of the line for the plot
	private ArrayList<Double> data; //floating point representation of the times in seconds
	private ArrayList<PointData> p_data; //object for tracking 
	private String[] times; //string representation of the average time
	private ArrayList<Date> dates; //date objects representing the days on which data was logged
	private String name;
	
	public ChartElement(String n, Color c)
	{
		name = n;
		this.c = c;
	}
	
	public void addData(double time, int d, int m, int y)
	{
		Date da = new Date(d,m,y);
		
		if(dates.size() > 0)
		{
			int i = 0;
			
			for(i = 0; dates.get(i).compare(da) < 1 && i < dates.size(); i++)
			{
				//do nothing
			}
			
			if(i == dates.size()) //if we did not find the date in the list
			{
				dates.add(da);
				p_data.add(new PointData(time));
			}
			else if(dates.get(i).compare(da) < 1) //we didn't find the date, but we found where it fits
			{
				if(i == 0)
				{
					dates.add(0, da);
					p_data.add(0, new PointData(time));
				}
				else
				{
					dates.add(i - 1, da);
					p_data.add(i - 1, new PointData(time));
				}
			}
			else //we found where the date fits
			{
				p_data.get(i).addData(time);
			}
		}
		else
		{
			dates.add(da);
			p_data.add(new PointData(time));
		}
	}
	

	public double[] getData()
	{
		double[] output = new double[p_data.size()];
		
		for(int i = 0; i < output.length; i++)
		{
			output[i] = p_data.get(i).getData();
		}
		
		return output;
	}
	
	
	private String buildDateString(int d, int m, int y)
	{
		String output = "" + d;
		output += "/" + m;
		output += "/" + y;
		
		return output;
	}
	
	public String[] getFormattedData()
	{
		String[] o_date = new String[dates.size()];
		
		for(int i = 0; i < dates.size(); i++)
		{
			o_date[i] = buildDateString(dates.get(i).getDay(), dates.get(i).getMonth(), dates.get(i).getYear());
		}
		
		return o_date;
	}
	
	public double[] getMinandMax()
	{
		double min = 99999.0;
		double max = 0.0;
		double[] extrema = {max, min};

		
		for(int i = 0; i < p_data.size(); i++)
		{
			if(p_data.get(i).getData() > max)
			{
				max = p_data.get(i).getData();
			}
			
			if(p_data.get(i).getData() < min)
			{
				min = p_data.get(i).getData();
			}
		}
		
		
		return extrema;
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public Color getColor()
	{
		return c;
	}
	
	public void setColor(Color c)
	{
		this.c = c;
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
	
	class PointData {
		
		int samples;
		double data;
		
		public PointData(double d)
		{
			data = d;
			samples = 1;
		}
		
		public double getData()
		{
			return data;
		}
		
		public int getSamples()
		{
			return samples;
		}
		
		//weighted averaging added
		public void addData(double n_data)
		{
			int n_samples = samples += 1;
			double o_pct = ((double)samples)/((double)n_samples);
			double n_pct = 1.0 / n_samples;
			
			data = (data * o_pct) + (n_data * n_pct);
			
			samples++;
		}
		
	}
}
