package com.technorage.demo.rules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
public static Date getDateFromStringTest(String date) throws ParseException{
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy HH:mm:ss"); //Tue Aug 31 10:20:56 SGT 1982
    	//String dateInString = "31/08/1982 10:20:56";
    	Date tDate = sdf.parse(date);
    	//log.info(tDate); //Tue Aug 31 10:20:56 SGT 1982
    	
    	return tDate;
    }
}
