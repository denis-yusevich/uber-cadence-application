package com.example.workflowworker.workflow.impl;


import com.example.activity.activities.WeatherRequestActivity;
import com.example.model.WeatherInfo;
import com.example.model.WeatherInfoResponseDto;
import com.example.workflow.WeatherWorkflow;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.workflow.Workflow;

import java.time.Duration;

public class WeatherWorkflowImpl implements WeatherWorkflow {

    private final WeatherRequestActivity activity;

    public WeatherWorkflowImpl() {
        this.activity = Workflow.newActivityStub(WeatherRequestActivity.class,
                new ActivityOptions.Builder()
                        .setScheduleToStartTimeout(Duration.ofSeconds(30))
                        .setStartToCloseTimeout(Duration.ofSeconds(30))
                        .build());
    }

    @Override
    public WeatherInfo downloadWeatherInfo(String city) {
        WeatherInfoResponseDto weatherInfo = activity.getWeatherInfo(city);
        return activity.storeWeatherInfo(weatherInfo);
    }
}
