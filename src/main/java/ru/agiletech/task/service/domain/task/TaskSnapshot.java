package ru.agiletech.task.service.domain.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import ru.agiletech.task.service.domain.sprint.SprintId;
import ru.agiletech.task.service.domain.teammate.TeammateId;

import java.time.LocalDate;
import java.util.Optional;

import static ru.agiletech.task.service.domain.task.Task.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TaskSnapshot {

    private final WorkFlowStatus    workFlowStatus;
    private final Priority          priority;
    private final SprintId sprintId;
    private final TeammateId assignee;
    private final Long              workHours;
    private final Long              workDays;
    private final LocalDate         startDate;
    private final LocalDate         endDate;

    public String getFlowStatus(){
        return this.workFlowStatus.getName();
    }

    public String getPriority(){
        return this.priority.getName();
    }

    public String getSprintId(){
        if(Optional.ofNullable(this.sprintId).isPresent())
            return this.sprintId.getId();

        return StringUtils.EMPTY;
    }

    public String getAssigneeId(){
        if(Optional.ofNullable(this.assignee).isPresent())
            return this.assignee.getId();

        return StringUtils.EMPTY;
    }

}
