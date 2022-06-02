package com.example.activityworker.activity;

import com.example.activityworker.activities.WeatherRequestActivityImpl;
import com.example.activityworker.activities.repo.WeatherInfoRepo;
import com.example.activityworker.service.WeatherRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


public class WeatherRequestActivityImplTest {

    @Mock
    private WeatherInfoRepo weatherInfoRepo;

    @Mock
    private WeatherRequestService weatherRequestService;

    private final WeatherRequestActivityImpl weatherRequestActivity = new WeatherRequestActivityImpl(
            weatherInfoRepo,
            weatherRequestService);

    @Test
    public void testRequest() {
        weatherRequestActivity.getWeatherInfo("London");
    }
}
