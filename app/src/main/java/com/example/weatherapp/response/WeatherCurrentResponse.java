package com.example.weatherapp.response;

import com.example.weatherapp.model.WeatherCurrent;

import java.util.List;

public class WeatherCurrentResponse {

    private List<WeatherCurrent> data;

    public List<WeatherCurrent> getWeatherCurrents() {
        return data;
    }

    public void setWeatherCurrents(List<WeatherCurrent> weatherCurrents) {
        this.data = weatherCurrents;
    }
}
