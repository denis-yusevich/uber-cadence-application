package com.example.workflowlauncher.service;

import com.example.workflowlauncher.dto.WeatherInfoDto;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutionException;

public interface WeatherInfoService {
    WeatherInfoDto downloadWeatherInfo(String city) throws TException;

    String reDownloadWeatherInfo(String city) throws TException, ExecutionException, InterruptedException;
}
