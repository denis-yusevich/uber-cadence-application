package com.example.activityworker.service.impl;

import com.example.activityworker.activities.WeatherRequestActivity;
import com.example.activityworker.service.WorkerFactoryService;
import com.example.factory.WorkflowClientFactory;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkerFactoryServiceImpl implements WorkerFactoryService {

    private static final String TASK_LIST = "task-list";

    private final WeatherRequestActivity activity;

    @Override
    public void startFactory() {
        WorkflowClient workflowClient = WorkflowClientFactory.createNewWorkflowClient();

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerActivitiesImplementations(activity);
        factory.start();
    }
}
