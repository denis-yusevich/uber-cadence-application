package com.example.workflowlauncher.service.impl;

import com.example.activityworker.dto.WeatherInfoDto;
import com.example.activityworker.model.WeatherInfo;
import com.example.factory.WorkflowClientFactory;
import com.example.workflowlauncher.service.WorkflowLauncher;
import com.example.workflowworker.workflow.WeatherWorkflow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class WorkflowLauncherImpl implements WorkflowLauncher {
    @Override
    public WeatherInfoDto launchWorkflow(String city) {
        WorkflowClient workflowClient = WorkflowClientFactory.createNewWorkflowClient();

        WeatherWorkflow workflow = workflowClient.newWorkflowStub(WeatherWorkflow.class,
                new WorkflowOptions.Builder().setExecutionStartToCloseTimeout(
                        Duration.ofMinutes(5)).setTaskList("task-list").build());
        WeatherInfo weatherInfo = workflow.downloadWeatherInfo(city);
        return transformToDto(weatherInfo);
    }

    private WeatherInfoDto transformToDto(WeatherInfo weatherInfo) {
        return new WeatherInfoDto(weatherInfo.getCity(), weatherInfo.getTemp());
    }
}