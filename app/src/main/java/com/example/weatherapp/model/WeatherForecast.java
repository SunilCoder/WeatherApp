package com.example.weatherapp.model;

public class WeatherForecast {
    private String valid_date;
    private long ts;
    private double temp;
    private double max_temp;
    private double min_temp;
    private double app_max_temp;
    private Weather weather;
    private double vis;
    private double wind_spd;

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





}
