package ru.agiletech.task.service.application.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskDTO extends RepresentationModel<TaskDTO> {

    @NotEmpty
    private String      summary;
    @NotEmpty
    private String      description;
    private String      workFlowStatus;
    private String      id;
    private String      sprintId;
    private LocalDate   startDate;
    private LocalDate   endDate;
    private Long        workHours;
    private Long        workDays;
    private String      assignee;
    private String      priority;

}
