package com.example.workflowlauncher.controller;

import com.example.workflowlauncher.service.WorkflowLauncher;
import com.uber.cadence.client.WorkflowException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WorkflowLaunchController {

    private final WorkflowLauncher workflowLauncher;

    @GetMapping(value = "/weather")
    public String launchWeatherWorkflow(@RequestParam String city) {
        return workflowLauncher.launchWorkflow(city).toString();
    }

    @ExceptionHandler(WorkflowException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(){
        return "Weather info was not found!";
    }
}
