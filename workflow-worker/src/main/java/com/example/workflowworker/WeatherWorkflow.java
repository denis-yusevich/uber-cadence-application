package com.example.workflowworker;

import com.example.activityworker.activities.model.WeatherInfo;
import com.uber.cadence.workflow.WorkflowMethod;

public interface WeatherWorkflow {

    @WorkflowMethod
    WeatherInfo downloadWeatherInfo(String city);
}
