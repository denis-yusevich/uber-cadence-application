package com.example.activityworker.activities;


import com.example.activity.activities.WeatherRequestActivity;
import com.example.activityworker.repo.WeatherInfoRepo;
import com.example.activityworker.service.WeatherRequestService;
import com.example.model.WeatherInfo;
import com.example.model.WeatherInfoResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeatherRequestActivityImpl implements WeatherRequestActivity {
    private final WeatherInfoRepo weatherInfoRepo;

    private final WeatherRequestService weatherRequestService;

    @Override
    public WeatherInfoResponseDto getWeatherInfo(String cityName) {
       return weatherRequestService.requestWeatherInfo(cityName);
    }

    @Override
    public WeatherInfo storeWeatherInfo(WeatherInfoResponseDto responseDto) {
        return weatherInfoRepo.save(new WeatherInfo(responseDto));
    }
}
