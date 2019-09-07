package com.example.fajarramadhan.scrolllikeinstagram;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String getCurrentTimeFormat(String timeFormat){
        String time = "";
        SimpleDateFormat df = new SimpleDateFormat(timeFormat);
        Calendar c = Calendar.getInstance();
        time = df.format(c.getTime());

        return time;
    }

    public static boolean checkTime(String end){
        String start = DateUtils.getCurrentDate();
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            long different = endDate.getTime() - startDate.getTime();
            if(different>0){
                return true; //future
            }
        }
        catch (Exception e){

        }

        return false; //past
    }

    public static boolean getDayDifferent(String prevDate, String currDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
        try {

            Date date1 = formatter.parse(prevDate);
            Date date2 = formatter.parse(currDate);
            //System.out.println(date);

            if (date1.getDay()!=date2.getDay()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static String dateTimeConvert(String myDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("MMM dd, yyyy");

        try {

            Date date = formatter.parse(myDate);
            //System.out.println(date);
            String output = formatter2.format(date);
            // System.out.println(output);
            return output;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;

    }

    public static String dateTimeConvert2(String myDate){//Wed Jul 18 20:00:09 GMT+08:00 2018 MMM dd hh:mm:ss yyyy
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");//June 27, 2018 at 12:56:27 PM UTC+8
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat formatter2 = new SimpleDateFormat("MMM dd, yyyy 'at' h:mm:ss a");//June 27, 2018 at 12:56:27 PM
        formatter2.setTimeZone(TimeZone.getDefault());
        try {

            Date date = formatter.parse(myDate);
            //System.out.println(date);
            String output = formatter2.format(date);
            // System.out.println(output);
            return output;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;

    }

    public static String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        //System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        return dateFormat.format(date);

    }

    public static String getCurrentTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        return dateFormat.format(date);

    }

    //preset the date and time when making request
    public static String getUserDate(){
        //SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL d, yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //20 January 2015

        return dateFormat.format(date);
    }

    public static String getUserTime(){
        //SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //15:59:48

        return dateFormat.format(date);

    }

    public static String getFutureDate(String date, int days, String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat(format);
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(convertToNormalTimezone(date,format)));
            c.add(Calendar.DATE, days-1);  // number of days to add
            date = sdf2.format(c.getTime());  // dt is now the new date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date convertStringToDate(String myDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return formatter.parse(myDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String convertToNormalTimezone(String myDate, String PATTERN){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getDisplayName();
        // Log.e("Date", "Timezone = "+ TimeZoneName);

        //for conversion string to date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        //for conversion date to server database date format (GMT)
        SimpleDateFormat formatter2 = new SimpleDateFormat(PATTERN);
        formatter2.setTimeZone(timezone);

        try {
            Date date = formatter.parse(myDate);
            // System.out.println(date);
            String output = formatter2.format(date);
            //System.out.println(output);
            return output;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return myDate;
    }

    public static String convertToNormalTimezone2(String myDate, String PATTERN){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getDisplayName();
        // Log.e("Date", "Timezone = "+ TimeZoneName);

        //for conversion string to date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        //for conversion date to server database date format (GMT)
        SimpleDateFormat formatter2 = new SimpleDateFormat(PATTERN);
        formatter2.setTimeZone(timezone);

        try {
            Date date = formatter.parse(myDate);
            // System.out.println(date);
            String output = formatter2.format(date);
            //System.out.println(output);
            return output;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return myDate;
    }

    public static String convertDateToPattern(Date myDate, String PATTERN){

        TimeZone timezone = TimeZone.getDefault();

        SimpleDateFormat formatter2 = new SimpleDateFormat(PATTERN);
        formatter2.setTimeZone(timezone);

        try {
            // System.out.println(date);
            String output = formatter2.format(myDate);
            //System.out.println(output);
            return output;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String convertToNormalTimezone3(String myDate, String PATTERN){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getDisplayName();
        // Log.e("Date", "Timezone = "+ TimeZoneName);

        //for conversion string to date
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        //for conversion date to server database date format (GMT)
        SimpleDateFormat formatter2 = new SimpleDateFormat(PATTERN);
        formatter2.setTimeZone(timezone);

        try {
            Date date = formatter.parse(myDate);
            // System.out.println(date);
            String output = formatter2.format(date);
            //System.out.println(output);
            return output;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return myDate;
    }

    //check time cant be earlier more than 30 mins
    public static boolean dateChecking2 (String selectedDate){

        //SimpleDateFormat formatter = new SimpleDateFormat("d LLLL yyyy HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("LLLL d, yyyy h:mm a");

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {

            Date date = formatter.parse(selectedDate);

            String start = formatter2.format(date);
            String end = getCurrentDate();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            long different = endDate.getTime() - startDate.getTime();
            //Log.e("CHECKTIME", "Diff = "+different);
            long secondsInMilli = 1000;
            long min =  secondsInMilli * 60 * 30; //30 minutes
            //Log.e("CHECKTIME", "Min = "+min);
            if (different >= min){
                return  true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean dateChecking (String selectedDate){
        SimpleDateFormat formatter = new SimpleDateFormat("d LLLL yyyy HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {

            Date date = formatter.parse(selectedDate);

            String end = formatter2.format(date);
            String start = getCurrentDate();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            long different = endDate.getTime() - startDate.getTime();

            long secondsInMilli = 1000;
            long min =  secondsInMilli * 60 * 30; //30 minutes

            if (different >= min){
                return  true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //used for date time picker
    public static String convertToGMT(String myDate){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getID();
        Log.e("Date", "Timezone = "+ TimeZoneName);

        Log.e("Date", "Locale = " + Locale.getDefault().toString());

        //for conversion string to date
        //SimpleDateFormat formatter = new SimpleDateFormat("d LLLL yyyy HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("LLLL d, yyyy h:mm a");
        formatter.setTimeZone(timezone);

        //for conversion date to server database date format (GMT)
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter2.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));

        try {
            Date date = formatter.parse(myDate);

            // System.out.println(date);
            String output = formatter2.format(date);


            //System.out.println(output);
            return output;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate;
    }

    //used for date time picker
    public static String convertToGMT2(String myDate){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getID();
        Log.e("Date", "Timezone = "+ TimeZoneName);

        Log.e("Date", "Locale = " + Locale.getDefault().toString());

        //for conversion string to date
        //SimpleDateFormat formatter = new SimpleDateFormat("d LLLL yyyy HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        formatter.setTimeZone(timezone);

        //for conversion date to server database date format (GMT)
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter2.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));

        try {
            Date date = formatter.parse(myDate);

            // System.out.println(date);
            String output = formatter2.format(date);


            //System.out.println(output);
            return output;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate;
    }

    //used for date time picker
    public static String convertToGMT3(String myDate){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getID();
        Log.e("Date", "Timezone = "+ TimeZoneName);

        Log.e("Date", "Locale = " + Locale.getDefault().toString());

        //for conversion string to date
        //SimpleDateFormat formatter = new SimpleDateFormat("d LLLL yyyy HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("LLLL d, yyyy h:mm a");
        formatter.setTimeZone(timezone);

        //for conversion date to server database date format (GMT)
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter2.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));

        try {
            Date date = formatter.parse(myDate);

            // System.out.println(date);
            String output = formatter2.format(date);


            //System.out.println(output);
            return output;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate;
    }

    //used for date time picker change just date if use time "HH:mm:ss"
    public static String convertToGMT4(String myDate, String myFormat){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getID();
        Log.e("Date", "Timezone = "+ TimeZoneName);

        Log.e("Date", "Locale = " + Locale.getDefault().toString());

        //for conversion string to date
        //SimpleDateFormat formatter = new SimpleDateFormat("d LLLL yyyy HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat(myFormat);
        formatter.setTimeZone(timezone);

        //for conversion date to server database date format (GMT)
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        formatter2.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));

        //comment on 15/12/17 - prevent from sent wrong date
//        try {
//            Date date = formatter.parse(myDate);
//
//             System.out.println(date);
//            String output = formatter2.format(date);
//
//
//            System.out.println(output);
//            return output;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        return myDate;
    }

    //used for date time picker
    public static String convertToGMT5(String myDate){

        //get device timezone
        TimeZone timezone = TimeZone.getDefault();
        String TimeZoneName = timezone.getID();
        Log.e("Date", "Timezone = "+ TimeZoneName);

        Log.e("Date", "Locale = " + Locale.getDefault().toString());

        //for conversion string to date
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        formatter.setTimeZone(timezone);

        //for conversion date to server database date format Apr 20, 2015
        SimpleDateFormat formatter2 = new SimpleDateFormat("LLL d, yyyy");
        formatter2.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));

        try {
            Date date = formatter.parse(myDate);

            // System.out.println(date);
            String output = formatter2.format(date);


            //System.out.println(output);
            return output;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate;
    }

    public static String DB_format(String myDate, Context ctx){
        //April 21, 2015 03:28:00
        // SimpleDateFormat formatter = new SimpleDateFormat("dd LLLL yyyy HH:mm:ss");
        //"April 23, 2015 04:14:00"
      /*  DateFormat formatter;
        final String format = Settings.System.getString(ctx.getContentResolver(), Settings.System.DATE_FORMAT);
       Log.e("DateFormat", format);
        if (TextUtils.isEmpty(format)) {
            formatter = android.text.format.DateFormat.getMediumDateFormat(ctx);
        } else {
            formatter = new SimpleDateFormat(format);
        }*/

        //get system date format
      /*  SimpleDateFormat df=(SimpleDateFormat) DateFormat.getDateFormat(ctx);
        String pattern=df.toPattern();

        //get system time format
        SimpleDateFormat df2=(SimpleDateFormat) DateFormat.getTimeFormat(ctx);
        String pattern2=df2.toPattern();

        String myDateTimeFormat = pattern + " " + pattern2;
        Log.e("DateFormat", myDateTimeFormat);*/

        return null;

    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400

    public static String getDifferenceDays(String start, String end){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try{
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : "+ endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays,
                    elapsedHours, elapsedMinutes, elapsedSeconds);

            if (elapsedDays!=0){
                if (elapsedDays == 1){
                    return elapsedDays + " day";
                }
                return elapsedDays + " days";
            }

            if (elapsedHours!=0){
                if (elapsedHours == 1){
                    return elapsedHours + " hour";
                }
                return  elapsedHours + " hours";
            }

            if (elapsedMinutes!=0){
                if (elapsedMinutes == 1){
                    return elapsedMinutes + " minute";
                }
                return elapsedMinutes + " minutes";
            }
        /*
            if (elapsedSeconds!=0){
                if (elapsedSeconds==1){
                    return elapsedSeconds + " second";
                }
                return elapsedSeconds + " seconds";
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "less than a minute";

    }

    public static String getDifferenceDays2(String start, String end){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try{
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : "+ endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;

            if (elapsedDays!=0){
                if (elapsedDays == 1){
                    return elapsedDays-1 + " day";
                }
                return elapsedDays-1 + " days";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "0 day";

    }

    /*public static String getDifferenceYears(String start, String end){


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            Interval interval =
                    new Interval(startDate.getTime(), endDate.getTime());
            Period period = interval.toPeriod();

            long elapsedYears = period.getYears();
            long elapsedMonths = period.getMonths();
            long elapsedWeeks = period.getWeeks();
            long elapsedDays = period.getDays();
            long elapsedHours = period.getHours();
            long elapsedMinutes = period.getMinutes();

            if (elapsedYears != 0) {
                if (elapsedYears == 1) {
                    return "About "+ elapsedYears + " year";
                }
                return "Over "+elapsedDays + " years";
            }

            if (elapsedMonths != 0) {
                if (elapsedMonths == 1) {
                    return "About "+ elapsedMonths + " month";
                }
                return elapsedMonths + " months";
            }

            if (elapsedWeeks != 0) {
                if (elapsedWeeks == 1) {
                    return  elapsedWeeks + " week";
                }
                return elapsedWeeks + " weeks";
            }

            if (elapsedDays != 0) {
                if (elapsedDays == 1) {
                    return elapsedDays + " day";
                }
                return elapsedDays + " days";
            }

            if (elapsedHours != 0) {
                if (elapsedHours == 1) {
                    return "About " + elapsedHours + " hour";
                }
                return "About " + elapsedHours + " hours";
            }

            if (elapsedMinutes != 0) {
                if (elapsedMinutes == 1) {
                    return elapsedMinutes + " minute";
                }
                return elapsedMinutes + " minutes";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "A few seconds";

        *//*System.out.printf(
                "%d years, %d months, %d days, %d hours, %d minutes, %d seconds%n",
                period.getYears(), period.getMonths(), period.getDays(),
                period.getHours(), period.getMinutes(), period.getSeconds());*//*



    }*/

    /*public static String getDifferenceSimple(Context ctx,String start, String end){


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            Interval interval =
                    new Interval(startDate.getTime(), endDate.getTime());
            Period period = interval.toPeriod();

            long elapsedYears = period.getYears();
            long elapsedMonths = period.getMonths();
            long elapsedWeeks = period.getWeeks();
            long elapsedDays = period.getDays();
            long elapsedHours = period.getHours();
            long elapsedMinutes = period.getMinutes();
            long elapsedSeconds = period.getSeconds();

            if (elapsedYears != 0) {
                if (elapsedYears == 1) {
                    return ctx.getString(R.string.year);
                }
                return elapsedYears + " " + ctx.getString(R.string.years);
            }

            if (elapsedMonths != 0) {
                if (elapsedMonths == 1) {
                    return  ctx.getString(R.string.month);
                }
                return elapsedMonths + " " + ctx.getString(R.string.months);
            }

            if (elapsedWeeks != 0) {
                if (elapsedWeeks == 1) {
                    return  ctx.getString(R.string.week);
                }
                return elapsedWeeks + " " + ctx.getString(R.string.weeks);
            }

            if (elapsedDays != 0) {
                if (elapsedDays == 1) {
                    return ctx.getString(R.string.day);
                }
                return elapsedDays + " " + ctx.getString(R.string.days);
            }

            if (elapsedHours != 0) {
                if (elapsedHours == 1) {
                    return  ctx.getString(R.string.hour);
                }
                return elapsedHours + " " + ctx.getString(R.string.hours);
            }

            if (elapsedMinutes != 0) {
                if (elapsedMinutes == 1) {
                    return  ctx.getString(R.string.min);
                }
                return elapsedMinutes + " " +ctx.getString(R.string.mins);
            }

            if (elapsedSeconds != 0) {
                if (elapsedSeconds == 1) {
                    return  ctx.getString(R.string.one_sec);
                }
                return elapsedSeconds + " " + ctx.getString(R.string.secs);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "1s";

        *//*System.out.printf(
                "%d years, %d months, %d days, %d hours, %d minutes, %d seconds%n",
                period.getYears(), period.getMonths(), period.getDays(),
                period.getHours(), period.getMinutes(), period.getSeconds());*//*

    }*/

    /*public static String getDifferenceSimpleAgo(String start, String end){


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            Interval interval =
                    new Interval(startDate.getTime(), endDate.getTime());
            Period period = interval.toPeriod();

            long elapsedYears = period.getYears();
            long elapsedMonths = period.getMonths();
            long elapsedWeeks = period.getWeeks();
            long elapsedDays = period.getDays();
            long elapsedHours = period.getHours();
            long elapsedMinutes = period.getMinutes();
            long elapsedSeconds = period.getSeconds();

            if (elapsedYears != 0) {
                if (elapsedYears == 1) {
                    return elapsedYears + " year ago";
                }
                return elapsedYears + " years ago";
            }

            if (elapsedMonths != 0) {
                if (elapsedMonths == 1) {
                    return elapsedMonths + " month ago";
                }
                return elapsedMonths + " months ago";
            }

            if (elapsedWeeks != 0) {
                if (elapsedWeeks == 1) {
                    return  elapsedWeeks + " week ago";
                }
                return elapsedWeeks + " weeks ago";
            }

            if (elapsedDays != 0) {
                if (elapsedDays == 1) {
                    return elapsedDays + " day ago";
                }
                return elapsedDays + " days ago";
            }

            if (elapsedHours != 0) {
                if (elapsedHours == 1) {
                    return elapsedHours + " hour ago";
                }
                return elapsedHours + " hours ago";
            }

            if (elapsedMinutes != 0) {
                if (elapsedMinutes == 1) {
                    return elapsedMinutes + " minute ago";
                }
                return elapsedMinutes + " minutes ago";
            }

            if (elapsedSeconds != 0) {
                if (elapsedSeconds == 1) {
                    return elapsedSeconds + " second ago";
                }
                return elapsedSeconds + " seconds ago";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "";

    }*/
}
