package com.example.workflowlauncher.service;

import com.example.activityworker.activities.dto.WeatherInfoDto;
import com.example.activityworker.activities.model.WeatherInfo;
import com.example.workflowworker.WeatherWorkflow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class WorkflowLauncherImpl implements WorkflowLauncher {
    @Override
    public WeatherInfoDto launchWorkflow(String city) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(
                                ClientOptions.newBuilder()
                                        .setHost("cadence")
                                        .setPort(7933)
                                        .build()),
                        WorkflowClientOptions.newBuilder().setDomain("test-domain").build());

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
