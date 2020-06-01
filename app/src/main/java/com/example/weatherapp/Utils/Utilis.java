package com.example.weatherapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utilis {

    public static void hideKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


    public static String  convertMIllistoDate(long millis) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day + " " + outputFormat.format(calendar.getTime());
    }

    public static String  convertMilistoTime(long millis){
        SimpleDateFormat outputFormat = new SimpleDateFormat("h:m", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return  outputFormat.format(calendar.getTime());

    }


    public static String getFormattedDate(String dateStr) {
        Log.d("june", "getFormattedDate: " + dateStr);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        try {
            Date date = inputFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String days = null;
            switch (day) {
                case 1:
                    days = "Sunday";
                    break;
                case 2:
                    days = "Monday";
                    break;
                case 3:
                    days = "Tuesday";
                    break;
                case 4:
                    days = "Wednesday";
                    break;
                case 5:
                    days = "Thursday";
                    break;

                case 6:
                    days = "Friday";
                    break;

                case 7:
                    days = "Saturday";
                    break;


            }


            return days + " " + outputFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }


    }

    public static String getDaysofTheweek(String data) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date = inputFormat.parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String days = "";

            switch (day) {
                case 1:
                    days = "Sunday";
                    break;
                case 2:
                    days = "Monday";
                    break;

                case 3:
                    days = "Tuesday";
                    break;
                case 4:
                    days = "Wednesday";
                    break;
                case 5:
                    days = "Thursday";
                    break;

                case 6:
                    days = "Friday";
                    break;

                case 7:
                    days = "Saturday";
                    break;
            }
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


}
