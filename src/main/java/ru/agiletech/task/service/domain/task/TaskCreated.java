package ru.agiletech.task.service.domain.task;

import lombok.Getter;
import ru.agiletech.task.service.domain.supertype.DomainEvent;

import java.util.Date;

@Getter
public class TaskCreated extends DomainEvent {

    private Project project;
    private TaskId  taskId;

    TaskCreated(Date    occurredOn,
                String  name,
                Project project,
                TaskId  taskId) {
        super(occurredOn, name);

        this.project    = project;
        this.taskId     = taskId;
    }

}
