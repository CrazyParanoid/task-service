package ru.agiletech.task.service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.agiletech.task.service.domain.task.DomainEventPublisher;
import ru.agiletech.task.service.domain.project.Project;
import ru.agiletech.task.service.domain.task.TaskCreated;
import ru.agiletech.task.service.domain.task.TaskId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCreatedEventPublisher implements DomainEventPublisher<TaskCreated> {

    private static final String OCCURRED_ON     = "occurredOn";
    private static final String NAME            = "name";
    private static final String PROJECT_KEY     = "key";
    private static final String TASK_ID         = "taskId";
    private static final String EVENT_NAME      = "eventName";

    private final Source source;

    @Override
    public void publish(List<TaskCreated> domainEvents) {
        try{
            for(TaskCreated event: domainEvents){
                Map<String, Object> serializedEvent = new HashMap<>();

                TaskId taskId = event.getTaskId();
                Project project = event.getProject();

                serializedEvent.put(OCCURRED_ON, event.getOccurredOn());
                serializedEvent.put(NAME, event.getName());
                serializedEvent.put(PROJECT_KEY, project.getKey());
                serializedEvent.put(TASK_ID, taskId.getId());

                source.output()
                        .send(MessageBuilder
                                .withPayload(serializedEvent)
                                .setHeader(EVENT_NAME, event.getName())
                                .build());
            }
        } catch (MessagingException ex){
            log.error(ex.getMessage());

            throw new MessagePublishingException(ex.getMessage(), ex);
        }
    }

}
