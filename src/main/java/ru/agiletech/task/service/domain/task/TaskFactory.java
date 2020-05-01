package ru.agiletech.task.service.domain.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.agiletech.task.service.domain.timetracker.TimeTracker;

@Component
@RequiredArgsConstructor
public class TaskFactory {

    private static final String DELIMITER = "-";

    private final TaskRepository taskRepository;

    public Task createTask(String summary,
                           String description,
                           String projectKey){
        Project project = Project.createFrom(projectKey);
        TaskId taskId = identifyTask(project);
        TimeTracker timeTracker = TimeTracker.create();

        return new Task(taskId,
                timeTracker,
                Task.Priority.LOW,
                project,
                Task.WorkFlowStatus.BACKLOG,
                summary,
                description);
    }

    private TaskId identifyTask(Project project){
        long taskCount = taskRepository.countTasksOfProject(project);
        taskCount++;

        String rawTaskId = project.getKey()
                + DELIMITER
                + taskCount;

        return TaskId.identifyTaskFrom(rawTaskId);
    }

}
