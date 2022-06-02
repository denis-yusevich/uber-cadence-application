package com.example.activityworker.activities;

import com.example.activityworker.model.WeatherInfo;
import com.example.activityworker.repo.WeatherInfoRepo;
import com.example.activityworker.service.WeatherRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeatherRequestActivityImpl implements WeatherRequestActivity {
    private final WeatherInfoRepo weatherInfoRepo;

    private final WeatherRequestService weatherRequestService;

    @Override
    public WeatherInfo getWeatherInfo(String cityName) {
       return weatherRequestService.requestWeatherInfo(cityName);
    }

    @Override
    public WeatherInfo storeWeatherInfo(WeatherInfo weatherInfo) {
        return weatherInfoRepo.save(weatherInfo);
    }
}
