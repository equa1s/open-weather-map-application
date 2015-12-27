package com.openweathermap.model.api;

import com.openweathermap.model.pojo.OpenWeatherPojo;
import com.openweathermap.model.utils.Constants;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface OpenWeatherApi {
    @GET(Constants.ENDPOINT)
    void getWeather(@Query("q") String city, @Query("appid") String appId, Callback<OpenWeatherPojo> callback);
}
