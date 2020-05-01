package ru.agiletech.task.service.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.agiletech.task.service.domain.task.Project;
import ru.agiletech.task.service.domain.task.Task;
import ru.agiletech.task.service.domain.task.TaskId;
import ru.agiletech.task.service.domain.task.TaskRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskDAO taskDAO;

    @Override
    public void save(Task task) {
        try{
            taskDAO.save(task);

        } catch (DataAccessException ex){
            log.error(ex.getMessage());

            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

    @Override
    public Set<Task> allTasks() {
        try{
            return new HashSet<>(taskDAO.findAll());

        } catch (DataAccessException ex){
            log.error(ex.getMessage());

            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

    @Override
    public Task taskOfId(TaskId taskId) {
        try{
            return taskDAO.findByTaskId(taskId)
                    .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id %s is not found", taskId.getId())));

        } catch (DataAccessException ex){
            log.error(ex.getMessage());

            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

    @Override
    public Set<Task> allTasksOfProject(Project project) {
        try{
            return new HashSet<>(taskDAO.findByProject(project));

        } catch (DataAccessException ex){
            log.error(ex.getMessage());

            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

}
