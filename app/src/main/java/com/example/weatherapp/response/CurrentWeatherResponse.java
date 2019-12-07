package com.example.weatherapp.response;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.Locations;


public class CurrentWeatherResponse {
    private Locations location;
    private CurrentWeather current;

    public Locations getLocations() {
        return location;
    }

    public void setLocations(Locations Location) {
        this.location = Location;
    }

    public CurrentWeather getCurrentWeather() {
        return current;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.current = currentWeather;
    }

}
