package com.example.weatherapplication.hw9_2;

import com.example.weatherapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Statics {

    public static HashMap<String, Integer> drawableMap = new HashMap<>();
    public static HashMap<String, String> stateMap = new HashMap<>();
    public static String currentLocation;
    public static String searchLocation;

    public static String locationDetails;
    public static String weatherDetails;

    public static ArrayList<String> prefs = new ArrayList<>();

    static {
        drawableMap.put("1000", R.drawable.ic_clear_day);
        drawableMap.put("1001", R.drawable.ic_cloudy);
        drawableMap.put("1100", R.drawable.ic_mostly_clear_day);
        drawableMap.put("1101", R.drawable.ic_partly_cloudy_day);
        drawableMap.put("1102", R.drawable.ic_mostly_clear_day);
        drawableMap.put("2000", R.drawable.ic_fog);
        drawableMap.put("2100", R.drawable.ic_fog_light);
        drawableMap.put("3000", R.drawable.weather_windy);
        drawableMap.put("3001", R.drawable.weather_windy);
        drawableMap.put("3002", R.drawable.weather_windy);
        drawableMap.put("4000", R.drawable.ic_drizzle);
        drawableMap.put("4001", R.drawable.ic_rain);
        drawableMap.put("4200", R.drawable.ic_rain_light);
        drawableMap.put("4201", R.drawable.ic_rain_heavy);
        drawableMap.put("5000", R.drawable.ic_snow);
        drawableMap.put("5001", R.drawable.ic_flurries);
        drawableMap.put("6000", R.drawable.ic_freezing_drizzle);
        drawableMap.put("6001", R.drawable.ic_freezing_rain);
        drawableMap.put("6200", R.drawable.ic_freezing_rain_light);
        drawableMap.put("6201", R.drawable.ic_freezing_rain_heavy);
        drawableMap.put("7000", R.drawable.ic_ice_pellets);
        drawableMap.put("7101", R.drawable.ic_ice_pellets_light);
        drawableMap.put("7102", R.drawable.ic_ice_pellets_heavy);
        drawableMap.put("8000", R.drawable.ic_tstorm);

        stateMap.put("1000", "Clear");
        stateMap.put("1001", "Cloudy");
        stateMap.put("1100", "Mostly Clear");
        stateMap.put("1101", "Partly Cloudy");
        stateMap.put("1102", "Mostly Cloudy");
        stateMap.put("2000", "Fog");
        stateMap.put("2100", "Light Fog");
        stateMap.put("3000", "Light Wind");
        stateMap.put("3001", "Wind");
        stateMap.put("3002", "Strong Wind");
        stateMap.put("4000", "Drizzle");
        stateMap.put("4001", "Rain");
        stateMap.put("4200", "Light Rain");
        stateMap.put("4201", "Heavy Rain");
        stateMap.put("5000", "Snow");
        stateMap.put("5001", "Flurries");
        stateMap.put("6000", "Freezing Drizzle");
        stateMap.put("6001", "Freezing Rain");
        stateMap.put("6200", "Light Freezing Rain");
        stateMap.put("6201", "Heavy Freezing Rain");
        stateMap.put("7000", "Ice Pellets");
        stateMap.put("7101", "Heavy Ice Pellets");
        stateMap.put("7102", "Light Ice Pellets");
        stateMap.put("8000", "Thunderstorm");
    }

    public static int getImage(String status) {
        if(drawableMap.containsKey(status)) {
            return drawableMap.get(status);
        }

        return R.drawable.ic_clear_day;
    }
}
