package ru.agiletech.task.service.eventbus;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import ru.agiletech.task.service.application.sprint.CommandProcessor;
import ru.agiletech.task.service.application.sprint.ScheduleToSprintCommand;

import javax.validation.Valid;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SprintStartedEventFilter implements PipeFilter<Map<String, Object>>{

    private static final String SPRINT_ID = "sprintId";
    private static final String TASK_ID = "taskId";

    private final CommandProcessor<ScheduleToSprintCommand> scheduleToSprintCommandProcessor;

    @StreamListener(Sink.INPUT)
    public void onEvent(@Valid Map<String, Object> serializedEvent){
        String rawSprintId = (String) serializedEvent.get(SPRINT_ID);
        String rawTaskId = (String) serializedEvent.get(TASK_ID);

        var command = new ScheduleToSprintCommand(rawSprintId, rawTaskId);

        scheduleToSprintCommandProcessor.process(command);
    }

}
