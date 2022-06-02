package com.example.activityworker.activities;

import com.example.activityworker.activities.model.WeatherInfo;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface WeatherRequestActivity {
    WeatherInfo getWeatherInfo(String cityName);

    WeatherInfo storeWeatherInfo(WeatherInfo weatherInfo);
}
