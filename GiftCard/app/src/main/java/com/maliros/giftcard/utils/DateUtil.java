package com.maliros.giftcard.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 02/06/2016.
 * Helper class for modifying dates
 */
public class DateUtil {
    public static final SimpleDateFormat DATE_FORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd-MM-yyyy");


    public static Calendar getDate(String date){
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            try {
                calendar.setTime(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS.parse(date));
                Log.d("calender", calendar.toString());
            } catch (ParseException e) {
                //DO nothing!!
                Log.d("cant convert", date);
            }
        }
        return calendar;
    }
}
