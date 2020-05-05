package ru.agiletech.task.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agiletech.task.service.Application;
import ru.agiletech.task.service.domain.sprint.SprintId;
import ru.agiletech.task.service.domain.task.*;
import ru.agiletech.task.service.domain.teammate.TeammateId;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static ru.agiletech.task.service.domain.task.Task.*;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class TestTask {

    @Autowired
    private TaskFactory     taskFactory;

    @Autowired
    private TaskRepository  taskRepository;

    private Task task;

    @Test
    public void testCreateTask(){
        this.task = createTask();

        taskRepository.save(this.task);

        TaskSnapshot snapshot = task.makeSnapshot();

        assertNotNull(task.taskId());
        assertNotNull(snapshot.getWorkFlowStatus());
        assertNotNull(snapshot.getPriority());

        Task newTask = createTask();

        TaskSnapshot newSnapshot = task.makeSnapshot();

        assertNotNull(newTask.taskId());
        assertNotNull(newSnapshot.getWorkFlowStatus());
        assertNotNull(newSnapshot.getPriority());
    }

    @Test
    public void testStartWork(){
        this.task = createTask();

        assignTeammate(task);
        task.startWork();

        TaskSnapshot snapshot = task.makeSnapshot();

        assertNotNull(task.taskId());
        assertNotNull(snapshot.getWorkFlowStatus());
        assertNotNull(snapshot.getPriority());
    }

    @Test
    public void testStopWork(){
        this.task = createTask();

        assignTeammate(task);

        task.startWork();
        task.stopWork();

        TaskSnapshot snapshot = task.makeSnapshot();

        assertNotNull(task.taskId());
        assertNotNull(snapshot.getWorkFlowStatus());
        assertNotNull(snapshot.getPriority());
    }

    @Test
    public void testAssignTeammate(){
        this.task = createTask();

        assignTeammate(task);

        TaskSnapshot snapshot = task.makeSnapshot();

        assertNotNull(task.taskId());
        assertNotNull(snapshot.getWorkFlowStatus());
        assertNotNull(snapshot.getPriority());
        assertNotNull(snapshot.getAssigneeId());
    }

    @Test
    public void testChangePriority(){
        this.task = createTask();

        Priority priority = Priority.HIGH;

        task.changePriority(priority);

        TaskSnapshot snapshot = task.makeSnapshot();

        assertNotNull(task.taskId());
        assertNotNull(snapshot.getWorkFlowStatus());
        assertNotNull(snapshot.getPriority());
    }

    @Test
    public void testScheduleToSprint(){
        this.task = createTask();

        String rawSprintId = UUID.randomUUID().toString();

        SprintId sprintId = SprintId.identifySprintFrom(rawSprintId);
        task.scheduleToSprint(sprintId);

        TaskSnapshot snapshot = task.makeSnapshot();

        assertNotNull(task.taskId());
        assertNotNull(snapshot.getWorkFlowStatus());
        assertNotNull(snapshot.getPriority());
        assertNotNull(snapshot.getSprintId());
    }

    @Test
    public void testNoteWorkHours(){
        this.task = createTask();

        assignTeammate(task);
        task.startWork();
        task.noteWorkHours(4);

        TaskSnapshot snapshot = task.makeSnapshot();

        assertNotNull(task.taskId());
        assertNotNull(snapshot.getWorkFlowStatus());
        assertNotNull(snapshot.getPriority());
        assertNotNull(snapshot.getWorkHours());
    }

    private Task createTask(){
        String summary = "New task";
        String description = "Description for new task";
        String projectKey = "TST";

        return taskFactory.createTask(summary,
                description,
                projectKey);
    }

    private void assignTeammate(Task task){
        String rawTeammateId = UUID.randomUUID().toString();

        TeammateId teammateId = TeammateId.identifyTeammateFrom(rawTeammateId);
        task.assignTeammate(teammateId);
    }

}
