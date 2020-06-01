package com.example.weatherapp.Utils;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.weatherapp.App;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.model.WeatherForecast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlarmReceiver extends BroadcastReceiver {
    Pref pref;
    String title;
    private List<WeatherForecast> weatherForecastLists;
    private Bitmap bmp;

    @Override
    public void onReceive(Context context, Intent intent) {

        pref = new Pref(context);
        if(pref.getLocation()!=null){
            title = pref.getLocation();
            weatherForecastLists = pref.getForecast();
        }

        String icon = weatherForecastLists.get(0).getWeather().getIcon();
        final String Url = "https://www.weatherbit.io/static/img/icons/" + icon + ".png";

        Bitmap contactPic = null;

        try {

            contactPic = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    try {
                        return Picasso.get().load(Url)
                                /*.resize(500, 500)*/
                                .get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent intent1 = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        int currentTemp = (int) weatherForecastLists.get(0).getTemp();
        int maxTemp = (int) weatherForecastLists.get(0).getMax_temp();
        int minTemp = (int) weatherForecastLists.get(0).getMin_temp();
        String temperature = ("High " + maxTemp + "°" + " | " + "Low " + minTemp + "°");
        String channelId = App.CHANNEL_ID;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(currentTemp + "° " + title)
                .setContentText(temperature)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if (contactPic != null) {
            builder.setLargeIcon(contactPic);
        } else {
            //builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_user_purple_light));
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }
}
