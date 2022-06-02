package com.example.activityworker.service;

import com.example.activityworker.model.WeatherInfo;

public interface WeatherRequestService {
    WeatherInfo requestWeatherInfo(String cityName);
}
