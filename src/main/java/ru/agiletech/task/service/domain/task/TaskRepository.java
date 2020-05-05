package ru.agiletech.task.service.domain.task;

import ru.agiletech.task.service.domain.project.Project;

import java.util.Set;

public interface TaskRepository {

    void save(Task task);

    Set<Task> allTasks();

    Task taskOfId(TaskId taskId);

    long countTasksOfProject(Project project);

}
