package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.weatherapp.Utils.AlarmReceiver;
import com.example.weatherapp.Utils.Pref;
import com.example.weatherapp.model.WeatherForecast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
        createAlarm();
        /*pref=new Pref(this);
        if(pref!=null){
            if(pref.getLocation()!=null){
                Intent intent= new Intent(this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }*/
    }

    private void createAlarm() {


        AlarmManager  alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        boolean hasAlarm = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_NO_CREATE) != null;
        Log.d("pantoon", "createAlarm: "+hasAlarm);
        if(!hasAlarm){
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 30);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }

       // alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+ 1000 * 60 * 60 * 24,pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+5000,pendingIntent);
    }
}
