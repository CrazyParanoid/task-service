package ru.agiletech.task.service.application.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Представление модели задачи")
public class TaskDTO extends RepresentationModel<TaskDTO> {

    @NotEmpty(message = "Отсутствует краткое описание задачи")
    @ApiModelProperty(required = true, value = "Краткое описание задачи")
    private String      summary;

    @NotEmpty(message = "Отсутствует полное описание задачи")
    @ApiModelProperty(position = 1, required = true, value = "Полное описание задачи")
    private String      description;

    @NotEmpty(message = "Отсутствует ключ проекта")
    @ApiModelProperty(position = 2, required = true, value = "Ключ проекта")
    private String      projectKey;

    @ApiModelProperty(position = 3, value = "Статус задачи", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String      workFlowStatus;

    @ApiModelProperty(position = 4, value = "Идентификатор задачи", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String      id;

    @ApiModelProperty(position = 5, value = "Идентификатор спринта", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String      sprintId;

    @ApiModelProperty(position = 6, value = "Дата начала выполнения задачи", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private LocalDate   startDate;

    @ApiModelProperty(position = 7, value = "Дата завершения задачи", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private LocalDate   endDate;

    @ApiModelProperty(position = 8, value = "Количество затраченных на выполнение задачи часов", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long        workHours;

    @ApiModelProperty(position = 9, value = "Количество затраченных на выполнение задачи дней", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long        workDays;

    @ApiModelProperty(position = 10, value = "Идентификатор исполнителя задачи", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String      assignee;

    @ApiModelProperty(position = 11, value = "Приоритет задачи", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String      priority;

}
