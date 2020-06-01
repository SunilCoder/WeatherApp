package com.example.weatherapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.weatherapp.model.WeatherForecast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Pref {

    private static final String APP_SHARED_PREF = Pref.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private final String KEY_WEATHER_FORECAST = "weather_forecast";
    private Gson gson;

    public Pref(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_SHARED_PREF, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveForeCastWeather(List<WeatherForecast> weatherForecast) {
        Log.d("jyaap", "saveForeCastWeather: " + weatherForecast);
        sharedPreferences.edit().putString(KEY_WEATHER_FORECAST, gson.toJson(weatherForecast)).apply();

    }

    public List<WeatherForecast> getForecast() {
        String weatherForecast = sharedPreferences.getString(KEY_WEATHER_FORECAST, null);
        Log.d("jyaap", "saveForeCastWeather???: " + weatherForecast);
        if (weatherForecast != null) {
            Log.d("jyaap", "saveForeCastWeather++: " + weatherForecast);
            List<WeatherForecast> weatherForecasts = new ArrayList<>();
            Type type = new TypeToken<List<WeatherForecast>>() {
            }.getType();
            weatherForecasts = gson.fromJson(weatherForecast, type);
            Log.d("jyaap", "saveForeCastWeather--: " + weatherForecasts);
            return weatherForecasts;
        } else {
            Log.d("jyaap", "saveForeCastWeather--: >>>>" );
            return null;
        }
    }

    public void saveLocationName(String name) {
        Log.d("kancha", "saveLocationName: "+name);
         sharedPreferences.edit().putString("Location", name).apply();
    }

    public void saveLastLocation(String name){
        sharedPreferences.edit().putString("sss",name).apply();
    }

    public String getLastLocation(){
        String location = sharedPreferences.getString("sss", null);
        if (location != null) {
            Log.d("kancha", "getLastLocation: "+location);
            return location;
        } else {
            return null;
        }
    }

    public String getLocation() {
        String locations = sharedPreferences.getString("Location", null);
        if (locations != null) {
            return locations;
        } else {
            return null;
        }
    }

    public void clearLocationName() {
        Log.d("kancha", "getLastLocation: bbbbbbbb");
        sharedPreferences.edit().remove("Location").commit();
    }
    public void clearWeatherForecast(){
        sharedPreferences.edit().remove(KEY_WEATHER_FORECAST).clear().apply();
    }

    public void clearData() {
        sharedPreferences.edit().clear().apply();
    }


}





















