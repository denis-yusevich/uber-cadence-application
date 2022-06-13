package com.example.workflowlauncher.controller;

import com.example.workflowlauncher.dto.WeatherInfoDto;
import com.example.workflowlauncher.service.WeatherInfoService;
import com.uber.cadence.client.WorkflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class WorkflowLaunchControllerTest {

    private static final String CITY = "Minsk";

    private static final String URL_TEMPLATE_WEATHER = "/weather?city=Minsk";

    private static final String URL_TEMPLATE_RERUN = "/rerun?city=Minsk";

    private MockMvc mockMvc;

    @Mock
    private WeatherInfoService service;

    @InjectMocks
    private WorkflowLaunchController controller;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new WebExceptionHandler())
                .build();
    }

    @Test
    public void shouldReturn200OnDownload() throws Exception {
        WeatherInfoDto weatherInfoDto = new WeatherInfoDto(CITY, 15d);
        when(service.downloadWeatherInfo(CITY)).thenReturn(weatherInfoDto);
        MockHttpServletResponse response =
                mockMvc.perform(post(URL_TEMPLATE_WEATHER).accept(MediaType.APPLICATION_JSON)).andReturn()
                        .getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), weatherInfoDto.toString());
    }

    @Test
    public void shouldReturn404OnDownload() throws Exception {
        when(service.downloadWeatherInfo(CITY)).thenThrow(WorkflowException.class);
        MockHttpServletResponse response =
                mockMvc.perform(post(URL_TEMPLATE_WEATHER).accept(MediaType.APPLICATION_JSON)).andReturn()
                        .getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(response.getContentAsString(), "Weather info was not found!");
    }

    @Test
    public void shouldReturn400OnDownload() throws Exception {
        String message = "Workflow for this city already exists, please use rerun method";
        when(service.downloadWeatherInfo(CITY))
                .thenThrow(
                        new IllegalArgumentException(message));
        MockHttpServletResponse response =
                mockMvc.perform(post(URL_TEMPLATE_WEATHER).accept(MediaType.APPLICATION_JSON)).andReturn()
                        .getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(response.getContentAsString(), message);
    }

    @Test
    public void shouldReturn200ReDownload() throws Exception {
        String uuid = UUID.randomUUID().toString();
        when(service.reDownloadWeatherInfo(CITY)).thenReturn(uuid);
        MockHttpServletResponse response =
                mockMvc.perform(post(URL_TEMPLATE_RERUN).accept(MediaType.APPLICATION_JSON)).andReturn()
                        .getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), uuid);
    }

    @Test
    public void shouldReturn400ReDownload() throws Exception {
        String message = "There wasn't any workflow run for this city";
        when(service.reDownloadWeatherInfo(CITY)).thenThrow(new IllegalArgumentException(message));
        MockHttpServletResponse response =
                mockMvc.perform(post(URL_TEMPLATE_RERUN).accept(MediaType.APPLICATION_JSON)).andReturn()
                        .getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(response.getContentAsString(), message);
    }
}
