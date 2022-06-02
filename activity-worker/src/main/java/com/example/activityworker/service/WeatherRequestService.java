package com.example.activityworker.service;

import com.example.activityworker.activities.model.WeatherInfo;

public interface WeatherRequestService {
    WeatherInfo requestWeatherInfo(String cityName);
}
