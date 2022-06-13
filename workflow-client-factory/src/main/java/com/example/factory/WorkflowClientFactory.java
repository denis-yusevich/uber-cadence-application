package com.example.factory;

import com.uber.cadence.StartTimeFilter;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Configuration
public class WorkflowClientFactory {

    public static final String DOMAIN = "weather-domain";

    private static final String CADENCE = "cadence";

    private static final int CADENCE_PORT = 7933;

    @Value("${cadence.workflow.startDateDelta}")
    private Integer daysDelta;

    @Bean
    public static IWorkflowService workflowServiceTChannel() {
        return new WorkflowServiceTChannel(ClientOptions.newBuilder()
                .setHost(CADENCE)
                .setPort(CADENCE_PORT)
                .build());
    }

    @Bean
    public static WorkflowClient createNewWorkflowClient(IWorkflowService service) {
        return WorkflowClient.newInstance(
                service,
                WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
    }

    @Bean
    @RequestScope
    public StartTimeFilter startTimeFilter() {
        return new StartTimeFilter()
                .setEarliestTime(TimeUnit.MILLISECONDS.toNanos(
                        Instant.now()
                                .minus(daysDelta, ChronoUnit.DAYS)
                                .toEpochMilli()))
                .setLatestTime(TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()));
    }
}
