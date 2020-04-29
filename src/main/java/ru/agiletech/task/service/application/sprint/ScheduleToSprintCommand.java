package ru.agiletech.task.service.application.sprint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleToSprintCommand implements Command{

    private String sprintId;
    private String taskId;

}
