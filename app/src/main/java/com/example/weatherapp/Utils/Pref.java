package com.example.weatherapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {

    private static final String APP_SHARED_PREF = Pref.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    public Pref(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public boolean saveLocationName(String name){
        return sharedPreferences.edit().putString("Location",name).commit();
    }

    public String getLocation(){
        String location = sharedPreferences.getString("Location",null);
        if(location!=null){
            return location;
        }else{
            return null;
        }
    }


}





















