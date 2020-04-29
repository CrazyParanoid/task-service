package ru.agiletech.task.service.eventbus;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agiletech.task.service.Application;
import ru.agiletech.task.service.application.task.TaskDTO;
import ru.agiletech.task.service.application.task.TaskService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@DirtiesContext
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class TestScheduleTaskToSprintEvent {

    private static final String SPRINT_ID = "sprint_id";
    private static final String TASK_ID = "task_id";

    @Autowired
    private Sink sink;

    @Autowired
    private TaskService taskService;

    @Test
    public void testScheduleTaskToSprint(){
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setSummary("New task");
        taskDTO.setDescription("Description for new task");

        String sprintId = UUID.randomUUID().toString();

        var createdTask = taskService.createTask(taskDTO);

        Map<String, Object> event = new HashMap<>();

        event.put(SPRINT_ID, sprintId);
        event.put(TASK_ID, createdTask.getId());

        sink.input()
                .send(MessageBuilder
                        .withPayload(event)
                        .build());
    }

}
