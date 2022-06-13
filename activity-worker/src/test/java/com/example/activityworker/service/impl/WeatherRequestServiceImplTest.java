package com.example.activityworker.service.impl;

import com.example.model.WeatherInfo;
import com.example.model.WeatherInfoResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.cadence.internal.common.CheckedExceptionWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherRequestServiceImplTest {

    private static final String CITY = "Minsk";

    @Mock
    private RestTemplate restTemplate;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    private WeatherRequestServiceImpl weatherRequestService;

    @BeforeEach
    public void init() {
        weatherRequestService = new WeatherRequestServiceImpl(restTemplate, objectMapper);
        ReflectionTestUtils.setField(weatherRequestService, "resourceUrl", "test.com");
        ReflectionTestUtils.setField(weatherRequestService, "apiKey", "testKey");
    }

    @Test
    public void shouldReturnWeatherInfo() throws JsonProcessingException {
        ResponseEntity mock = Mockito.mock(ResponseEntity.class);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(mock);
        when(mock.getBody()).thenReturn("{\"main\":{\"temp\":15}}");
        WeatherInfoResponseDto weatherInfo = weatherRequestService.requestWeatherInfo(CITY);
        verify(restTemplate).getForEntity(anyString(), any());
        verify(objectMapper).readTree(any(String.class));
        assertEquals(weatherInfo.getTemp(), 15);
    }

    @Test
    public void shouldThrowAnExceptionWhileProcessingJson() throws JsonProcessingException {
        ResponseEntity mock = Mockito.mock(ResponseEntity.class);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(mock);
        when(mock.getBody()).thenReturn("{main:{temp:15}}");
        assertThrows(CheckedExceptionWrapper.class, () -> weatherRequestService.requestWeatherInfo(CITY));
        verify(restTemplate).getForEntity(anyString(), any());
        verify(objectMapper).readTree(any(String.class));
    }

    @Test
    public void shouldThrowExceptionAfterHttpRequest() throws JsonProcessingException {
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> weatherRequestService.requestWeatherInfo(CITY));
        verify(restTemplate).getForEntity(anyString(), any());
        verify(objectMapper, never()).readTree(any(String.class));
    }

}
