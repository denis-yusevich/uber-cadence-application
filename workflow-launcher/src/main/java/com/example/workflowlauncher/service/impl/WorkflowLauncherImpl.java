package com.example.workflowlauncher.service.impl;

import com.example.factory.WorkflowClientFactory;
import com.example.model.WeatherInfo;
import com.example.workflow.WeatherWorkflow;
import com.example.workflowlauncher.dto.WeatherInfoDto;
import com.example.workflowlauncher.service.WorkflowLauncher;
import com.uber.cadence.ResetWorkflowExecutionRequest;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowLauncherImpl implements WorkflowLauncher {

    private final WorkflowClient workflowClient;

    private final IWorkflowService workflowService;

    @Value("${cadence.workflow.rerunReason}")
    private String rerunReason;

    @Value("${cadence.workflow.decisionFinishEventId}")
    private Integer decisionFinishEventId;

    @Value("${cadence.workflow.taskList}")
    private String taskList;

    @Override
    public WeatherInfoDto launchWorkflow(String city) {
        WeatherWorkflow workflow = workflowClient.newWorkflowStub(WeatherWorkflow.class,
                new WorkflowOptions.Builder()
                        .setWorkflowId(city)
                        .setExecutionStartToCloseTimeout(Duration.ofMinutes(5))
                        .setTaskList(taskList)
                        .build());
        WeatherInfo weatherInfo = workflow.downloadWeatherInfo(city);
        return transformToDto(weatherInfo);
    }

    @Override
    public String reLaunchWorkflow(WorkflowExecution execution) throws TException {
        var rerunRequest = prepareRerunRequest(execution);
        return workflowService.ResetWorkflowExecution(rerunRequest).getRunId();
    }

    private ResetWorkflowExecutionRequest prepareRerunRequest(WorkflowExecution execution) {
        return new ResetWorkflowExecutionRequest()
                .setDomain(WorkflowClientFactory.DOMAIN)
                .setDecisionFinishEventId(decisionFinishEventId)
                .setRequestId(UUID.randomUUID().toString())
                .setReason(rerunReason)
                .setWorkflowExecution(execution);
    }

    private WeatherInfoDto transformToDto(WeatherInfo weatherInfo) {
        return new WeatherInfoDto(weatherInfo.getCity(), weatherInfo.getTemp());
    }
}
