package com.example.activityworker.service.impl;

import com.example.activityworker.model.WeatherInfo;
import com.example.activityworker.service.WeatherRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.cadence.workflow.Workflow;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherRequestServiceImpl implements WeatherRequestService {

    private static final String RESOURCE_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    private static final String API_KEY = "713cf1047e85452b6709178b7f603014";

    @Override
    public WeatherInfo requestWeatherInfo(String cityName) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(RESOURCE_URL, cityName, API_KEY);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return transformResponse(cityName, response);
        } catch (Exception ex) {
            throw Workflow.wrap(ex);
        }
    }

    private WeatherInfo transformResponse(String cityName, ResponseEntity<String> response)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        double temp = root.at("/main/temp").asDouble();
        System.out.println(response);
        WeatherInfo weatherInfoDto = new WeatherInfo(cityName, temp);
        System.out.println(weatherInfoDto);
        return weatherInfoDto;
    }
}
