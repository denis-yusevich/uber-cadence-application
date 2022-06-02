package com.example.activityworker;

import com.example.activityworker.service.WorkerFactoryService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class ActivityWorkerApplication implements CommandLineRunner {

    private final WorkerFactoryService workerFactoryService;

    public static void main(String[] args) {
        SpringApplication.run(ActivityWorkerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        workerFactoryService.startFactory();
    }
}
