package com.example.weatherapp.response;

import com.example.weatherapp.model.WeatherForecast;

import java.util.List;

public class WeatherForecastResponse {
    private List<WeatherForecast> data;
    private String city_name;

    public List<WeatherForecast> getData() {
        return data;
    }

    public void setData(List<WeatherForecast> data) {
        this.data = data;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
