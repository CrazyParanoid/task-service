package ru.agiletech.task.service.application.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agiletech.task.service.domain.task.Task;
import ru.agiletech.task.service.domain.task.TaskId;
import ru.agiletech.task.service.domain.task.TaskRepository;
import ru.agiletech.task.service.domain.task.TeammateId;

import java.util.HashSet;
import java.util.Set;

import static ru.agiletech.task.service.domain.task.Task.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository    taskRepository;
    private final TaskAssembler     taskAssembler;

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        log.info("Create task");
        Task task = create(taskDTO.getSummary(),
                taskDTO.getDescription());

        String id = task.taskId();

        log.info("Task with id {} has been created", id);
        taskRepository.save(task);

        log.info("Task with id {} has been saved", id);

        return taskAssembler.writeDTO(task);
    }

    @Override
    @Transactional
    public void changePriorityOfTask(String rawTaskId,
                                     String rawPriority) {
        log.info("Change priority for task with id {}", rawTaskId);

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        Task task = taskRepository.taskOfId(taskId);

        Priority priority = Priority.fromName(rawPriority);
        task.changePriority(priority);

        log.info("Priority has been changed for task with id {}", rawTaskId);

        taskRepository.save(task);

        log.info("Task with id {} has been saved", rawTaskId);
    }

    @Override
    @Transactional
    public void assignTeammateToTask(String rawTaskId,
                                     String rawTeammateId) {
        log.info("Assign teammate for task with id {}", rawTaskId);

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        Task task = taskRepository.taskOfId(taskId);

        TeammateId teammateId = TeammateId.identifyTeammateFrom(rawTeammateId);
        task.assignTeammate(teammateId);

        log.info("Teammate has been assigned for task with id {}", rawTaskId);

        taskRepository.save(task);

        log.info("Task with id {} has been saved", rawTaskId);
    }

    @Override
    @Transactional
    public void noteWorkHoursForTask(String rawTaskId,
                                     long   workHours) {
        log.info("Note work hours for task with id {}", rawTaskId);

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        Task task = taskRepository.taskOfId(taskId);

        task.noteWorkHours(workHours);
        log.info("Work hours have been noted for task with id {}", rawTaskId);

        taskRepository.save(task);

        log.info("Task with id {} has been saved", rawTaskId);
    }

    @Override
    @Transactional
    public void startWorkOnTask(String rawTaskId) {
        log.info("Start working on task with id {}", rawTaskId);

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        Task task = taskRepository.taskOfId(taskId);

        task.startWork();
        log.info("Working has been started on task with id {}", rawTaskId);

        taskRepository.save(task);

        log.info("Task with id {} has been saved", rawTaskId);
    }

    @Override
    @Transactional
    public void stopWorkOnTask(String rawTaskId) {
        log.info("Stop working on task with id {}", rawTaskId);

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        Task task = taskRepository.taskOfId(taskId);

        task.stopWork();
        log.info("Working has been stopped on task with id {}", rawTaskId);

        taskRepository.save(task);

        log.info("Task with id {} has been saved", rawTaskId);
    }

    @Override
    @Transactional
    public TaskDTO searchTaskById(String rawTaskId) {
        log.info("Search task with id {}", rawTaskId);

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        Task task = taskRepository.taskOfId(taskId);

        log.info("Task with id {} has been found", rawTaskId);

        return taskAssembler.writeDTO(task);
    }

    @Override
    @Transactional
    public Set<TaskDTO> searchAllTasks() {
        log.info("Search all created tasks");

        Set<Task> tasks = taskRepository.allTasks();
        Set<TaskDTO> taskDTOS = new HashSet<>();

        if(CollectionUtils.isNotEmpty(tasks))
            tasks.forEach(task -> taskDTOS.add(taskAssembler.writeDTO(task)));

        log.info("All created tasks have been found");

        return taskDTOS;
    }

}
