package com.example.workflowlauncher.sevice.impl;

import com.example.workflowlauncher.service.WorkflowLauncher;
import com.example.workflowlauncher.service.impl.WeatherInfoServiceImpl;
import com.uber.cadence.StartTimeFilter;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.WorkflowExecutionInfo;
import com.uber.cadence.serviceclient.IWorkflowService;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherInfoServiceImplTest {

    private static final String CITY = "Minsk";

    @Mock
    private WorkflowLauncher workflowLauncher;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private IWorkflowService workflowService;

    @Mock
    private StartTimeFilter startTimeFilter;

    @InjectMocks
    private WeatherInfoServiceImpl weatherInfoService;

    @Test
    public void shouldLaunchNewWorkflow() throws TException {
        when(workflowService.ListClosedWorkflowExecutions(any()).getExecutions()).thenReturn(Collections.emptyList());
        when(workflowService.ListOpenWorkflowExecutions(any()).getExecutions()).thenReturn(Collections.emptyList());
        weatherInfoService.downloadWeatherInfo(CITY);
        verify(workflowLauncher).launchWorkflow(CITY);
    }

    @Test
    public void shouldThrowExceptionInsteadOfLaunchingNewWorkflow() throws TException {
        when(workflowService.ListClosedWorkflowExecutions(any()).getExecutions()).thenReturn(prepareExecutionInfo());
        when(workflowService.ListOpenWorkflowExecutions(any()).getExecutions()).thenReturn(Collections.emptyList());
        assertThrows(IllegalArgumentException.class, () -> weatherInfoService.downloadWeatherInfo(CITY));
        verify(workflowLauncher, never()).launchWorkflow(CITY);
    }

    @Test
    public void shouldRelaunchWorkflow() throws TException, ExecutionException, InterruptedException {
        when(workflowService.ListClosedWorkflowExecutions(any()).getExecutions()).thenReturn(prepareExecutionInfo());
        when(workflowService.ListOpenWorkflowExecutions(any()).getExecutions()).thenReturn(Collections.emptyList());
        weatherInfoService.reDownloadWeatherInfo(CITY);
        verify(workflowLauncher).reLaunchWorkflow(any());

    }

    @Test
    public void shouldThrowExceptionInsteadOfReLaunching() throws TException, ExecutionException, InterruptedException {
        when(workflowService.ListClosedWorkflowExecutions(any()).getExecutions()).thenReturn(Collections.emptyList());
        when(workflowService.ListOpenWorkflowExecutions(any()).getExecutions()).thenReturn(Collections.emptyList());
        assertThrows(IllegalArgumentException.class, () -> weatherInfoService.reDownloadWeatherInfo(CITY));
        verify(workflowLauncher, never()).reLaunchWorkflow(any());
    }


    private List<WorkflowExecutionInfo> prepareExecutionInfo() {
        return List.of(
                new WorkflowExecutionInfo().setExecution(
                        new WorkflowExecution().setWorkflowId(CITY)));
    }
}
