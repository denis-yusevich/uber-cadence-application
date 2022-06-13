package com.example.activityworker.service;

import com.example.model.WeatherInfoResponseDto;

public interface WeatherRequestService {
    WeatherInfoResponseDto requestWeatherInfo(String cityName);
}
