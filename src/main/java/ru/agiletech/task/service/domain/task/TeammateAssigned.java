package ru.agiletech.task.service.domain.task;

import lombok.Getter;
import ru.agiletech.task.service.domain.supertype.DomainEvent;
import ru.agiletech.task.service.domain.teammate.TeammateId;

import java.util.Date;

@Getter
public class TeammateAssigned extends DomainEvent {

    private TeammateId  assignee;
    private TaskId      taskId;

    TeammateAssigned(Date           occurredOn,
                     String         name,
                     TeammateId     assignee,
                     TaskId         taskId) {
        super(occurredOn, name);

        this.assignee   = assignee;
        this.taskId     = taskId;
    }

}
