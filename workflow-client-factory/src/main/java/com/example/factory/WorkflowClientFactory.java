package com.example.factory;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;

public class WorkflowClientFactory {

    private static final String DOMAIN = "weather-domain";

    private static final String CADENCE = "cadence";

    private static final int CADENCE_PORT = 7933;

    public static WorkflowClient createNewWorkflowClient() {
        return WorkflowClient.newInstance(
                new WorkflowServiceTChannel(ClientOptions.newBuilder()
                        .setHost(CADENCE)
                        .setPort(CADENCE_PORT)
                        .build()),
                WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
    }
}
