package ru.agiletech.task.service.domain.task;

import java.util.Set;

public interface TaskRepository {

    void save(Task task);

    Set<Task> allTasks();

    Task taskOfId(TaskId taskId);

    Set<Task> allTasksOfProject(Project project);

}
