package com.example.workflowlauncher.controller;

import com.example.workflowlauncher.service.WeatherInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class WorkflowLaunchController {
    private final WeatherInfoService service;

    @PostMapping(value = "/weather")
    public String launchWeatherWorkflow(@RequestParam String city) throws TException {
        return service.downloadWeatherInfo(city).toString();
    }

    @PostMapping(value = "/rerun")
    public String rerunWeatherWorkflow(@RequestParam String city)
            throws TException, ExecutionException, InterruptedException {
        return service.reDownloadWeatherInfo(city);
    }
}
