package com.example.weatherapp.response;

import com.example.weatherapp.model.WeatherForecast;

import java.util.List;

public class WeatherForecastResponse {
    private List<WeatherForecast> data;

    public List<WeatherForecast> getData() {
        return data;
    }

    public void setData(List<WeatherForecast> data) {
        this.data = data;
    }
}
