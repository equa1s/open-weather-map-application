package com.openweathermap.controllers;

import android.util.Log;

import com.openweathermap.model.api.OpenWeatherRestAdapter;
import com.openweathermap.model.pojo.OpenWeatherPojo;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OpenWeatherRestController {

    private ResponseCallbackListener responseCallbackListener;
    private OpenWeatherRestAdapter openWeatherRestAdapter;
    private final String TAG = getClass().getSimpleName();

    public OpenWeatherRestController(ResponseCallbackListener responseCallbackListener) {
        this.responseCallbackListener = responseCallbackListener;
        this.openWeatherRestAdapter = new OpenWeatherRestAdapter();
    }

    public void startFetch(String city, String appId) {

        getResponseCallbackListener().onFetchStart();

        getOpenWeatherRestAdapter().getOpenWeatherApi(city, appId, new Callback<OpenWeatherPojo>() {

            @Override
            public void success(OpenWeatherPojo openWeatherPojo, Response response) {
                try {
                    Log.d(TAG, openWeatherPojo.toString());
                    getResponseCallbackListener().onFetchProgress(openWeatherPojo);
                } catch (Exception e) {
                    e.printStackTrace();
                    getResponseCallbackListener().onFetchFailed();
                }
                getResponseCallbackListener().onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, error.getMessage());
                getResponseCallbackListener().onFetchFailed();
            }

        });

    }

    public OpenWeatherRestAdapter getOpenWeatherRestAdapter() {
        return openWeatherRestAdapter;
    }


    public ResponseCallbackListener getResponseCallbackListener() {
        return responseCallbackListener;
    }

    public interface ResponseCallbackListener {
        void onFetchStart();
        void onFetchProgress(OpenWeatherPojo mOpenWeatherPojo);
        void onFetchComplete();
        void onFetchFailed();
    }
}
