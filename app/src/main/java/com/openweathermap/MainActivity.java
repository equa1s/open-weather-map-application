package com.openweathermap;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.openweathermap.controllers.OpenWeatherRestController;
import com.openweathermap.controllers.TemperatureController;
import com.openweathermap.model.pojo.Coord;
import com.openweathermap.model.pojo.Main;
import com.openweathermap.model.pojo.OpenWeatherPojo;
import com.openweathermap.model.pojo.Weather;
import com.openweathermap.model.utils.Constants;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements OpenWeatherRestController.ResponseCallbackListener,
        SearchView.OnQueryTextListener {

    private final String TAG = getClass().getSimpleName();
    private OpenWeatherRestController mOpenWeatherRestController;
    private TextView city, temp, desc, pressure, humidity, lon, lat;
    private LinearLayout startLayout, resultLayout, progressLayout, anotherInfoLayout, errorLayout;
    private ImageView imageViewWeather;


    public MainActivity() {
        mOpenWeatherRestController = new OpenWeatherRestController(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem search = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
            searchView.setQueryHint("Please enter city");
            searchView.setOnQueryTextListener(this);
            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean queryTextFocused) {
                    if (!queryTextFocused) {
                        search.collapseActionView();
                        searchView.setQuery("", false);
                    }
                }
            });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Log.d(TAG, "Item selected: " + item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        city                = (TextView) findViewById(R.id.textViewCity);
        temp                = (TextView) findViewById(R.id.textViewTemp);
        desc                = (TextView) findViewById(R.id.textViewDesc);
        pressure            = (TextView) findViewById(R.id.textViewPressure);
        humidity            = (TextView) findViewById(R.id.textViewHumid);
        lon                 = (TextView) findViewById(R.id.textViewLon);
        lat                 = (TextView) findViewById(R.id.textViewLat);
        startLayout         = (LinearLayout) findViewById(R.id.startLayout);
        progressLayout      = (LinearLayout) findViewById(R.id.progressLayout);
        anotherInfoLayout   = (LinearLayout) findViewById(R.id.anotherInfoLayout);
        resultLayout        = (LinearLayout) findViewById(R.id.resultLayout);
        errorLayout         = (LinearLayout) findViewById(R.id.errorLayout);
        imageViewWeather    = (ImageView) findViewById(R.id.imageView);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            setSupportActionBar(mActionBarToolbar);
    }

    /**
     * Start request
     */
    @Override
    public void onFetchStart() {
        if(startLayout.getVisibility() == View.VISIBLE) {
            startLayout.setVisibility(View.GONE);
        }
        progressLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Get response
     * @param mOpenWeatherPojo received object
     */
    @Override
    public void onFetchProgress(OpenWeatherPojo mOpenWeatherPojo) {

        Weather mWeather = mOpenWeatherPojo.getWeather().get(0);
        Coord mCoord = mOpenWeatherPojo.getCoord();
        Main mMain = mOpenWeatherPojo.getMain();

        /**
         * Add data to view's
         */
        Picasso.with(getApplicationContext()).load(Constants.CONDITIONS[getIndex(mWeather.getIcon())]).into(imageViewWeather);
            city.setText(String.valueOf(mOpenWeatherPojo.getName() + " " + mOpenWeatherPojo.getSys().getCountry()));
            desc.setText(mWeather.getDescription());
            temp.setText(String.valueOf(TemperatureController.toCelsius(mMain.getTemp()) + "°"));
            pressure.setText(String.valueOf(mMain.getPressure()));
            humidity.setText(String.valueOf(mMain.getHumidity()));
            lat.setText(String.valueOf(mCoord.getLat()));
            lon.setText(String.valueOf(mCoord.getLon()));

        resultLayout.setVisibility(View.VISIBLE);
        anotherInfoLayout.setVisibility(View.VISIBLE);
        if(errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Fetch complete
     */
    @Override
    public void onFetchComplete() {
        progressLayout.setVisibility(View.GONE);
    }

    /**
     * Show error on the screen
     */
    @Override
    public void onFetchFailed() {
        Toast.makeText(getApplicationContext(), "Error occurred. Please try again!", Toast.LENGTH_SHORT).show();
        if(resultLayout.getVisibility() == View.VISIBLE && anotherInfoLayout.getVisibility() == View.VISIBLE) {
            resultLayout.setVisibility(View.GONE);
            anotherInfoLayout.setVisibility(View.GONE);
        }
        if(progressLayout.getVisibility() == View.VISIBLE) {
            progressLayout.setVisibility(View.GONE);
        }
        if(errorLayout.getVisibility() != View.VISIBLE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Search bar
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        String typedCity = query.toLowerCase();
            mOpenWeatherRestController.startFetch(typedCity, Constants.APP_ID);
        return true;
    }

    /**
     *
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     *
     * @param s input String
     * @return index of drawable
     */
    private int getIndex(String s) {
        return Integer.parseInt(s.replaceAll("\\D+", ""));
    }
}
