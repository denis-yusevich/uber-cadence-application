package com.example.workflowworker;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

public class WorkflowWorkerApplication {

    private static final String DOMAIN = "test-domain";

    private static final String TASK_LIST = "task-list";

    public static void main(String[] args) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.newBuilder()
                                .setHost("cadence")
                                .setPort(7933)
                                .build()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerWorkflowImplementationTypes(WeatherWorkflowImpl.class);

        factory.start();
    }

}
