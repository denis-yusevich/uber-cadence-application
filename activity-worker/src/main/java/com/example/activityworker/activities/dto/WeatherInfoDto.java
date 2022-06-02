package com.example.activityworker.activities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherInfoDto {
    String cityName;
    double temp;

    @Override
    public String toString() {
        return String.format("Air temperature in %s is %s degrees", cityName, temp);
    }
}
