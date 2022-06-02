package com.example.workflowworker.workflow.impl;

import com.example.activityworker.activities.WeatherRequestActivity;
import com.example.activityworker.model.WeatherInfo;
import com.example.workflowworker.workflow.WeatherWorkflow;
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
        WeatherInfo weatherInfo = activity.getWeatherInfo(city);
        return activity.storeWeatherInfo(weatherInfo);
    }
}
