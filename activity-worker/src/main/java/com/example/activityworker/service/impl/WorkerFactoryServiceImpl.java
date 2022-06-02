package com.example.activityworker.service.impl;


import com.example.activityworker.activities.WeatherRequestActivity;
import com.example.activityworker.service.WorkerFactoryService;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkerFactoryServiceImpl implements WorkerFactoryService {

    private static final String DOMAIN = "test-domain";

    private static final String TASK_LIST = "task-list";

    private final WeatherRequestActivity activity;

    @Override
    public void startFactory() {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.newBuilder()
                                .setHost("cadence")
                                .setPort(7933)
                                .build()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerActivitiesImplementations(activity);
        factory.start();
    }
}
