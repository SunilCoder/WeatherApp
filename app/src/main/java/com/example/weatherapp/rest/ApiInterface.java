package com.example.weatherapp.rest;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.response.CurrentWeatherResponse;
import com.example.weatherapp.response.WeatherCurrentResponse;
import com.example.weatherapp.response.WeatherForecastResponse;

import java.lang.ref.SoftReference;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    /*@GET("current")
    Call<CurrentWeatherResponse> getCurrentWeather(@Query("access_key")String Key, @Query("query")String location);
*/

    @GET("current")
    Call<WeatherCurrentResponse>getCurrentWeather(
            @Query("city")String city,
            @Query("key")String key
    );

    @GET("forecast/daily")
    Call<WeatherForecastResponse>getForecastWeather(@Query("city") String city,@Query("days") int days,@Query("key")String key);

}
