package util;

import entity.Item;
import entity.Staff;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessDate {
	/**
	 * Combine date(MM/dd/yyyy) and time(HH:mm:ss) into a Date
	 * @param date MM/dd/yyyy
	 * @param time HH:mm:ss
	 * @return combined date
	 */
	public static Date combineDateAndTime(String date, String time) {
		if ((null == date) || (null == time)) {
			System.out.println("Date and time are null. This order is invalid!");
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date combinedDate = null;
		try {
			combinedDate = sdf.parse(date + " " + time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return combinedDate;
	}
	
	/**
	 * Get a date(MM/dd/yyyy) which type is string
	 * @param date inputed date
	 * @return formatted date string
	 */
	public static String getDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(date);
	}
	
	/**
	 * Get a time(HH:mm:ss) which type is string
	 * @param date inputed date
	 * @return formatted time string
	 */
	public static String getTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * Get system date(MM/dd/yyyy) which type is string
	 * @return formatted date string
	 */
	public static String getSystemDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(new Date());
	}
	
	/**
	 * Get system time(HH:mm:ss) which type is string
	 * @return formatted time string
	 */
	public static String getSystemTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}

}
