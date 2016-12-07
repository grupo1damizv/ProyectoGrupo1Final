package com.izv.dam.newquip.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilFecha {

    public static long dateToLong(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
            Date fecha = sdf.parse(date);
            return fecha.getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    public static String formatDate() {
        Date date = new Date();
        Log.v("fecha", formatStringDate("yyyy-MM-dd_hh-mm-ss", date.getTime()));
        return formatStringDate("yyyy-MM-dd_hh-mm-ss", date.getTime());
    }

    public static String formatStringDate(String formatStr, long date) {
        if (date == 0) {
            return null;
        }
        return android.text.format.DateFormat.format(formatStr, date).toString();
    }

}