package com.example.weatherapp;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


public class App extends Application {
    public static final String CHANNEL_ID="channel1";
    private static App app;
    public static App getInstance(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        createNotificationChannel();
    }


    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name ="Weather App";
            String description ="Make sound and vibrate";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID,name,importance);
            channel1.setDescription(description);
            channel1.enableVibration(true);
            channel1.enableLights(true);
            channel1.setShowBadge(true);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }
}
