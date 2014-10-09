package com.technorage.demo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	
public static Date getDateFromString(String date) throws ParseException{
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy HH:mm:ss"); //Tue Aug 31 10:20:56 SGT 1982
    	//String dateInString = "31/08/1982 10:20:56";
    	Date tDate = sdf.parse(date);
    	//log.info(tDate); //Tue Aug 31 10:20:56 SGT 1982
    	
    	return tDate;
    }

public static String getFormattedDate(Locale locale){
	
	Date date = new Date();
	DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	
	String formattedDate = dateFormat.format(date);
	
	return formattedDate;
	}
}

