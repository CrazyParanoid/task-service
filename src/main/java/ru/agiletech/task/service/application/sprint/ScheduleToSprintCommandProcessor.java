package ru.agiletech.task.service.application.sprint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.agiletech.task.service.domain.task.SprintId;
import ru.agiletech.task.service.domain.task.Task;
import ru.agiletech.task.service.domain.task.TaskId;
import ru.agiletech.task.service.domain.task.TaskRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleToSprintCommandProcessor
        implements CommandProcessor<ScheduleToSprintCommand> {

    private final TaskRepository taskRepository;

    @Override
    public void process(ScheduleToSprintCommand command) {
        var taskId = TaskId.identifyTaskFrom(command.getTaskId());
        var sprintId = SprintId.identifySprintFrom(command.getSprintId());

        log.info("Schedule task with id {} to sprint with id {}", taskId.getId(),
                sprintId.getId());

        Task task = taskRepository.taskOfId(taskId);

        task.scheduleToSprint(sprintId);
        log.info("Task with id {} has been scheduled to sprint with id {}", taskId.getId(),
                sprintId.getId());

        taskRepository.save(task);
        log.info("Task with id {} has been saved", taskId.getId());
    }

}
