package ru.agiletech.task.service.input.ports.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.agiletech.task.service.application.task.TaskDTO;
import ru.agiletech.task.service.application.task.TaskService;
import ru.agiletech.task.service.input.ports.presentation.hateoas.LinksUtil;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(value = "REST-ресурс задачи")
public class TaskResource {

    private final TaskService taskService;

    @PostMapping(value = "/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Создать задачу", code = 201)
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO){
        var createdTask = taskService.createTask(taskDTO);
        LinksUtil.addLinks(createdTask);

        return createdTask;
    }

    @GetMapping(value = "/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Поиск задачи по идентификатору")
    public TaskDTO getTask(@PathVariable String id){
        var task = taskService.searchTaskById(id);
        LinksUtil.addLinks(task);

        return task;
    }

    @GetMapping(value = "/tasks")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Поиск всех созданных задач")
    public Set<TaskDTO> getAllTasks(){
        Set<TaskDTO> tasks = taskService.searchAllTasks();

        if(CollectionUtils.isNotEmpty(tasks))
            tasks.forEach(LinksUtil::addLinks);

        return tasks;
    }

    @PutMapping(value = "/tasks/{id}")
    @ApiOperation(value = "Изменить приоритет задачи")
    public ResponseEntity<Void> changePriority(@PathVariable String id,
                                               @RequestParam String priority){
        taskService.changePriorityOfTask(id,
                priority);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/tasks/{id}/start")
    @ApiOperation(value = "Начать выполнение задачи")
    public ResponseEntity<Void> startTask(@PathVariable String id){
        taskService.startWorkOnTask(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/tasks/{id}/stop")
    @ApiOperation(value = "Завершить выполнение задачи")
    public ResponseEntity<Void> stopTask(@PathVariable String id){
        taskService.stopWorkOnTask(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/tasks/{id}/assignee")
    @ApiOperation(value = "Назначить исполнителя задачи")
    public ResponseEntity<Void> assignTeammate(@PathVariable(name = "id") String taskId,
                                               @RequestParam              String teammateId){
        taskService.assignTeammateToTask(taskId,
                teammateId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/tasks/{id}/tracker")
    @ApiOperation(value = "Отметить затраченные на выполнение задачи часы")
    public ResponseEntity<Void> noteWorkHours(@PathVariable(name = "id") String taskId,
                                              @RequestParam              Long   workHours){
        taskService.noteWorkHoursForTask(taskId,
                workHours);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
