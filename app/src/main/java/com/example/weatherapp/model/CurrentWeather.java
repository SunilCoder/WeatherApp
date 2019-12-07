package com.example.weatherapp.model;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeather {

    private String observation_time;
    private int temperature;
    private int weather_code;
    private ArrayList<String> weather_icons = new ArrayList<>();
    private ArrayList<String> weather_descriptions = new ArrayList<>();
    private double wind_speed;
    private int wind_degree;
    private String wind_dir;
    private int pressure;
    private double precip;
    private int humidity;
    private double cloudcover;
    private double feelslike;
    private int uv_index;
    private String visibility;

    public String getObservaton_time() {
        return observation_time;
    }

    public void setObservaton_time(String observaton_time) {
        this.observation_time = observaton_time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }

    public List<String> getWeather_icons() {
        return weather_icons;
    }

    public void setWeather_icons(ArrayList<String> weather_icons) {
        this.weather_icons = weather_icons;
    }

    public List<String> getWeather_descriptions() {
        return weather_descriptions;
    }

    public void setWeather_descriptions(ArrayList<String> weather_descriptions) {
        this.weather_descriptions = weather_descriptions;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public void setWind_degree(int wind_degree) {
        this.wind_degree = wind_degree;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public int getWind_pressure() {
        return pressure;
    }

    public void setWind_pressure(int wind_pressure) {
        this.pressure = wind_pressure;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(double cloudcover) {
        this.cloudcover = cloudcover;
    }

    public double getFeelslike() {
        return feelslike;
    }

    public void setFeelslike(double feelslike) {
        this.feelslike = feelslike;
    }

    public int getUv_index() {
        return uv_index;
    }

    public void setUv_index(int uv_index) {
        this.uv_index = uv_index;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }


}
