package com.example.weatherapp.model;

import java.io.Serializable;

public class WeatherForecast implements Serializable {
    private String valid_date;
    private long ts;
    private double temp;
    private double max_temp;
    private double min_temp;
    private double app_max_temp;
    private Weather weather;
    private double vis;
    private double uv;
    private double rh;
    private long sunrise_ts;
    private long sunset_ts;
    private double wind_spd;
    private double wind_dir;
    private String wind_cdir_full;

    public double getWind_spd(){return wind_spd;}

    public void setWind_spd(double wind_spd){
        this.wind_spd= wind_spd;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(double max_temp) {
        this.max_temp = max_temp;
    }

    public double getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(double min_temp) {
        this.min_temp = min_temp;
    }

    public double getapp_max_Temp() {
        return app_max_temp;
    }

    public void setapp_max_Temp(double app_max_Temp) {
        this.app_max_temp = app_max_Temp;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getVis() {
        return vis;
    }

    public void setVis(double vis) {
        this.vis = vis;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public long getSunrise_ts() {
        return sunrise_ts;
    }

    public void setSunrise_ts(long sunrise_ts) {
        this.sunrise_ts = sunrise_ts;
    }

    public long getSunset_ts() {
        return sunset_ts;
    }

    public void setSunset_ts(long sunset_ts) {
        this.sunset_ts = sunset_ts;
    }

    public double getRh() {
        return rh;
    }

    public void setRh(double rh) {
        this.rh = rh;
    }

    public double getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(double wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_cdir_full() {
        return wind_cdir_full;
    }

    public void setWind_cdir_full(String wind_cdir_full) {
        this.wind_cdir_full = wind_cdir_full;
    }
}
