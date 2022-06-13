package com.example.activity.activities;

import com.example.model.WeatherInfo;
import com.example.model.WeatherInfoResponseDto;

public interface WeatherRequestActivity {
    WeatherInfoResponseDto getWeatherInfo(String cityName);

    WeatherInfo storeWeatherInfo(WeatherInfoResponseDto weatherInfo);
}
