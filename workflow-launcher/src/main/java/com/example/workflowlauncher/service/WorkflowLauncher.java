package com.example.workflowlauncher.service;

import com.example.workflowlauncher.dto.WeatherInfoDto;
import com.uber.cadence.WorkflowExecution;
import org.apache.thrift.TException;

import java.util.concurrent.ExecutionException;

public interface WorkflowLauncher {
    WeatherInfoDto launchWorkflow(String city) throws TException;

    String reLaunchWorkflow(WorkflowExecution execution) throws TException, ExecutionException, InterruptedException;
}
