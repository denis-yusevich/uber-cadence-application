package com.example.workflowworker;

import com.example.factory.WorkflowClientFactory;
import com.example.workflowworker.workflow.impl.WeatherWorkflowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

public class WorkflowWorkerApplication {

    private static final String TASK_LIST = "task-list";

    public static void main(String[] args) {
        WorkflowClient workflowClient = WorkflowClientFactory.createNewWorkflowClient(
                WorkflowClientFactory.workflowServiceTChannel());

        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerWorkflowImplementationTypes(WeatherWorkflowImpl.class);

        factory.start();
    }

}
