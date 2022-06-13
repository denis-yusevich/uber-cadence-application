package com.example.workflowlauncher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherInfoDto {
    private String cityName;
    private double temp;

    @Override
    public String toString() {
        return String.format("Air temperature in %s is %s degrees", cityName, temp);
    }
}
