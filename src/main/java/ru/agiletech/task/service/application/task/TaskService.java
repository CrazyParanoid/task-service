package ru.agiletech.task.service.application.task;

import java.util.Set;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    void changePriorityOfTask(String rawTaskId,
                              String rawPriority);

    void assignTeammateToTask(String rawTaskId,
                              String rawTeammateId);

    void noteWorkHoursForTask(String rawTaskId,
                              long workHours);

    void startWorkOnTask(String rawTaskId);

    void stopWorkOnTask(String rawTaskId);

    TaskDTO searchTaskById(String rawTaskId);

    Set<TaskDTO> searchAllTasks();

}
