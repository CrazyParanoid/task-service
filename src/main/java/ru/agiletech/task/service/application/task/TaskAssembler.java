package ru.agiletech.task.service.application.task;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;
import ru.agiletech.task.service.domain.task.Task;

@Service
@RequiredArgsConstructor
public class TaskAssembler implements RepresentationModelAssembler<Task, TaskDTO> {

    private final ModelMapper modelMapper;

    @Override
    @NotNull
    public TaskDTO toModel(@NotNull Task task) {
        var taskDTO = modelMapper.map(task, TaskDTO.class);

        var snapshot = task.makeSnapshot();

        taskDTO.setId(task.taskId());
        taskDTO.setWorkFlowStatus(snapshot.getFlowStatus());
        taskDTO.setAssignee(snapshot.getAssigneeId());
        taskDTO.setStartDate(snapshot.getStartDate());
        taskDTO.setEndDate(snapshot.getEndDate());
        taskDTO.setWorkDays(snapshot.getWorkDays());
        taskDTO.setWorkHours(snapshot.getWorkHours());
        taskDTO.setPriority(snapshot.getPriority());

        return taskDTO;
    }

}
