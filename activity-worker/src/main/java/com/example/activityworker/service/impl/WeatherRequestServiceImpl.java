package com.example.activityworker.service.impl;

import com.example.activityworker.service.WeatherRequestService;
import com.example.model.WeatherInfo;
import com.example.model.WeatherInfoResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.cadence.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherRequestServiceImpl implements WeatherRequestService {

    @Value("${weather.request.resourceUrl}")
    private String resourceUrl;

    @Value("${weather.request.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public WeatherInfoResponseDto requestWeatherInfo(String cityName) {
        try {
            String url = String.format(resourceUrl, cityName, apiKey);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return transformResponse(cityName, response);
        } catch (Exception ex) {
            throw Workflow.wrap(ex);
        }
    }

    private WeatherInfoResponseDto transformResponse(String cityName, ResponseEntity<String> response)
            throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        double temp = root.at("/main/temp").asDouble();
        return new WeatherInfoResponseDto(cityName, temp);
    }
}
