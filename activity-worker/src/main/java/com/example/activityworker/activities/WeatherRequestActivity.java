package com.example.activityworker.activities;

import com.example.activityworker.model.WeatherInfo;


public interface WeatherRequestActivity {
    WeatherInfo getWeatherInfo(String cityName);

    WeatherInfo storeWeatherInfo(WeatherInfo weatherInfo);
}
