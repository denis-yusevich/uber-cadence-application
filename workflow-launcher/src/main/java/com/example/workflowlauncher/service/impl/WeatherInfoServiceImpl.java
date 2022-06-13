package com.example.workflowlauncher.service.impl;

import com.example.factory.WorkflowClientFactory;
import com.example.workflowlauncher.dto.WeatherInfoDto;
import com.example.workflowlauncher.service.WeatherInfoService;
import com.example.workflowlauncher.service.WorkflowLauncher;
import com.uber.cadence.ListClosedWorkflowExecutionsRequest;
import com.uber.cadence.ListOpenWorkflowExecutionsRequest;
import com.uber.cadence.StartTimeFilter;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.WorkflowExecutionInfo;
import com.uber.cadence.serviceclient.IWorkflowService;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class WeatherInfoServiceImpl implements WeatherInfoService {

    private final WorkflowLauncher workflowLauncher;

    private final IWorkflowService workflowService;

    private final StartTimeFilter startTimeFilter;

    @Override
    public WeatherInfoDto downloadWeatherInfo(String city) throws TException {
        checkIfWorkflowAlreadyExists(city);
        return workflowLauncher.launchWorkflow(city);
    }

    @Override
    public String reDownloadWeatherInfo(String city) throws TException, ExecutionException,
            InterruptedException {

        List<WorkflowExecutionInfo> executions = getWorkflowExecutions();
        WorkflowExecution execution = getWorkflowExecutionById(city, executions).orElseThrow(
                () -> new IllegalArgumentException("There wasn't any workflow run for this city"));

        return workflowLauncher.reLaunchWorkflow(execution);
    }

    private void checkIfWorkflowAlreadyExists(String city) throws TException {
        List<WorkflowExecutionInfo> executions = getWorkflowExecutions();
        Optional<WorkflowExecution> workflowExecutionById = getWorkflowExecutionById(city, executions);
        if (workflowExecutionById.isPresent()) {
            throw new IllegalArgumentException("Workflow for this city already exists, please use rerun method");
        }
    }

    private List<WorkflowExecutionInfo> getWorkflowExecutions() throws TException {
        var listWorkflowExecutionsRequest =
                new ListClosedWorkflowExecutionsRequest()
                        .setStartTimeFilter(startTimeFilter)
                        .setDomain(WorkflowClientFactory.DOMAIN);

        var listOpenWorkflowExecutionsRequest =
                new ListOpenWorkflowExecutionsRequest()
                        .setStartTimeFilter(startTimeFilter)
                        .setDomain(WorkflowClientFactory.DOMAIN);

        List<WorkflowExecutionInfo> openExecutions =
                workflowService.ListOpenWorkflowExecutions(listOpenWorkflowExecutionsRequest).getExecutions();

        List<WorkflowExecutionInfo> closedExecutions =
                workflowService.ListClosedWorkflowExecutions(listWorkflowExecutionsRequest).getExecutions();

        List<WorkflowExecutionInfo> result = new ArrayList<>(openExecutions);
        result.addAll(closedExecutions);
        return result;


    }

    private Optional<WorkflowExecution> getWorkflowExecutionById(String city, List<WorkflowExecutionInfo> executions) {
        return executions.stream()
                .map(WorkflowExecutionInfo::getExecution)
                .filter(execution -> execution.getWorkflowId().equalsIgnoreCase(city))
                .findFirst();
    }

}
