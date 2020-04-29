package ru.agiletech.task.service.application.task;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.agiletech.task.service.domain.task.Task;

@Service
@RequiredArgsConstructor
public class TaskAssembler {

    private final ModelMapper modelMapper;
    TaskDTO writeDTO(Task task){
        var taskDTO = modelMapper.map(task, TaskDTO.class);

        taskDTO.setId(task.taskId());
        taskDTO.setWorkFlowStatus(task.workFlowStatus());
        taskDTO.setAssignee(task.assigneeId());
        taskDTO.setStartDate(task.startDate());
        taskDTO.setEndDate(task.endDate());
        taskDTO.setWorkDays(task.workDays());
        taskDTO.setWorkHours(task.workHours());
        taskDTO.setPriority(task.priority());

        return taskDTO;
    }

}
