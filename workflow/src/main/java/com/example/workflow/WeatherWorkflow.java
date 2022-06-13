package com.example.workflow;

import com.example.model.WeatherInfo;
import com.uber.cadence.workflow.WorkflowMethod;

public interface WeatherWorkflow {

    @WorkflowMethod
    WeatherInfo downloadWeatherInfo(String city);
}
