package com.example.workflowlauncher.service;

import com.example.activityworker.activities.dto.WeatherInfoDto;

public interface WorkflowLauncher {
    WeatherInfoDto launchWorkflow(String city);
}
