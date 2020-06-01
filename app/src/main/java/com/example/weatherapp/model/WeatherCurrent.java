package com.example.weatherapp.model;

public class WeatherCurrent {

    private String pod;
    private String datetime;
    private double temp;
    private long ts;
    private String ob_time;
    private Weather weather;
    private double app_temp;
    private String city_name;
    private double wind_spd;
    private double precip;
    private double vis;
    private double uv;
    private String sunrise;
    private String sunset;
    private double rh;
    private double wind_dir;
    private String wind_cdir_full;


    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getOb_time() {
        return ob_time;
    }

    public void setOb_time(String ob_time) {
        this.ob_time = ob_time;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getApp_temp() {
        return app_temp;
    }

    public void setApp_temp(double app_temp) {
        this.app_temp = app_temp;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public double getWind_spd() {
        return wind_spd;
    }

    public void setWind_spd(double wind_spd) {
        this.wind_spd = wind_spd;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
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

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
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
