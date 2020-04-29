package ru.agiletech.task.service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agiletech.task.service.domain.task.Task;
import ru.agiletech.task.service.domain.task.TaskId;

import java.util.Optional;

public interface TaskDAO extends JpaRepository<Task, Long> {

    Optional<Task> findByTaskId(TaskId taskId);

}
