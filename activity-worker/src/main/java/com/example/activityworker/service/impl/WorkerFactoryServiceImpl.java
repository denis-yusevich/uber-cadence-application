package com.example.activityworker.service.impl;

import com.example.activity.activities.WeatherRequestActivity;
import com.example.activityworker.service.WorkerFactoryService;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerFactoryServiceImpl implements WorkerFactoryService {

    private static final String TASK_LIST = "task-list";

    private final WeatherRequestActivity activity;

    private final WorkflowClient workflowClient;

    @Override
    public void startFactory() {
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerActivitiesImplementations(activity);
        factory.start();
    }
}
