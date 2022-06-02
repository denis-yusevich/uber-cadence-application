package com.example.workflowlauncher.service;

import com.example.activityworker.dto.WeatherInfoDto;

public interface WorkflowLauncher {
    WeatherInfoDto launchWorkflow(String city);
}
