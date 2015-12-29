package com.openweathermap.model.utils;

import com.openweathermap.R;

/**
 * Created by elia on 25.12.15.
 */
public interface Constants {
    String BASE_URL = "http://api.openweathermap.org";
    String ENDPOINT = "/data/2.5/weather";
    String APP_ID = "c23c79cbf9fc0a5c635c5fd7e7462234";
    Integer[] CONDITIONS = {
            null, R.drawable.d01,R.drawable.d02,R.drawable.d03,R.drawable.d04,
            null,null,null,null,R.drawable.d09,R.drawable.d10,R.drawable.d11,
            null,R.drawable.d13,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,R.drawable.d50
    };
}
