package com.example.workflowworker.workflow;

import com.example.activityworker.model.WeatherInfo;
import com.uber.cadence.workflow.WorkflowMethod;

public interface WeatherWorkflow {

    @WorkflowMethod
    WeatherInfo downloadWeatherInfo(String city);
}
