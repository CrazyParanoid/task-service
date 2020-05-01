package ru.agiletech.task.service.domain.task;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import ru.agiletech.task.service.domain.timetracker.TimeTracker;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TaskFactory {

    private static final long START_TASK_ID_COUNT_VALUE = 1L;
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
        Set<Task> tasks = taskRepository.allTasksOfProject(project);
        String projectKey = project.getKey();

        if(CollectionUtils.isEmpty(tasks)){
            String rawTaskId = projectKey
                    + DELIMITER
                    + START_TASK_ID_COUNT_VALUE;

            return TaskId.identifyTaskFrom(rawTaskId);
        }

        long sequence = tasks.stream().map(Task::taskId)
                .map(taskId -> taskId.replace(projectKey + DELIMITER, StringUtils.EMPTY))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(START_TASK_ID_COUNT_VALUE);
        sequence++;

        String rawTaskId = projectKey
                + DELIMITER
                + sequence;

        return TaskId.identifyTaskFrom(rawTaskId);
    }

}
