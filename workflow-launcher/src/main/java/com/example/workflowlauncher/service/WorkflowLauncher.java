package com.example.workflowlauncher.service;

import com.example.workflowlauncher.dto.WeatherInfoDto;

public interface WorkflowLauncher {
    WeatherInfoDto launchWorkflow(String city);
}
