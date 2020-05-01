package ru.agiletech.task.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agiletech.task.service.Application;
import ru.agiletech.task.service.domain.task.*;

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

        assertNotNull(task.taskId());
        assertNotNull(task.workFlowStatus());
        assertNotNull(task.priority());

        Task newTask = createTask();

        assertNotNull(newTask.taskId());
        assertNotNull(newTask.workFlowStatus());
        assertNotNull(newTask.priority());
    }

    @Test
    public void testStartWork(){
        this.task = createTask();

        assignTeammate(task);
        task.startWork();

        assertNotNull(task.taskId());
        assertNotNull(task.workFlowStatus());
        assertNotNull(task.priority());
    }

    @Test
    public void testStopWork(){
        this.task = createTask();

        assignTeammate(task);

        task.startWork();
        task.stopWork();

        assertNotNull(task.taskId());
        assertNotNull(task.workFlowStatus());
        assertNotNull(task.priority());
    }

    @Test
    public void testAssignTeammate(){
        this.task = createTask();

        assignTeammate(task);

        assertNotNull(task.taskId());
        assertNotNull(task.workFlowStatus());
        assertNotNull(task.priority());
        assertNotNull(task.assigneeId());
    }

    @Test
    public void testChangePriority(){
        this.task = createTask();

        Priority priority = Priority.HIGH;

        task.changePriority(priority);

        assertNotNull(task.taskId());
        assertNotNull(task.workFlowStatus());
        assertNotNull(task.priority());
    }

    @Test
    public void testScheduleToSprint(){
        this.task = createTask();

        String rawSprintId = UUID.randomUUID().toString();

        SprintId sprintId = SprintId.identifySprintFrom(rawSprintId);
        task.scheduleToSprint(sprintId);

        assertNotNull(task.taskId());
        assertNotNull(task.workFlowStatus());
        assertNotNull(task.priority());
        assertNotNull(task.sprintId());
    }

    @Test
    public void testNoteWorkHours(){
        this.task = createTask();

        assignTeammate(task);
        task.startWork();
        task.noteWorkHours(4);

        assertNotNull(task.taskId());
        assertNotNull(task.workFlowStatus());
        assertNotNull(task.priority());
        assertNotNull(task.workHours());
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
