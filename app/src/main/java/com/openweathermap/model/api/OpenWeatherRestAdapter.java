package com.openweathermap.model.api;

import com.google.gson.GsonBuilder;
import com.openweathermap.model.pojo.OpenWeatherPojo;
import com.openweathermap.model.utils.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class OpenWeatherRestAdapter {

    private OpenWeatherApi openWeatherApi;

    public OpenWeatherRestAdapter() {
        OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(20000, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(20000, TimeUnit.MILLISECONDS);

        GsonBuilder gsonBuilder = new GsonBuilder();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gsonBuilder.create()))
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(Constants.BASE_URL)
                .build();

        openWeatherApi = restAdapter.create(OpenWeatherApi.class);
    }

    public void getOpenWeatherApi(String city, String appId, Callback<OpenWeatherPojo> openWeatherPojoCallback) {
        openWeatherApi.getWeather(city, appId, openWeatherPojoCallback);
    }
}
