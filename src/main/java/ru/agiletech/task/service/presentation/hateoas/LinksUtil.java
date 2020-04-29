package ru.agiletech.task.service.presentation.hateoas;

import lombok.experimental.UtilityClass;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import ru.agiletech.task.service.application.task.TaskDTO;
import ru.agiletech.task.service.presentation.TaskResource;

@UtilityClass
public class LinksUtil {

    public void addLinks(TaskDTO taskDTO){
        addSelfLink(taskDTO);
        addAllTasksLink(taskDTO);
        addAssigneeLink(taskDTO);
        addPriorityLink(taskDTO);
        addStartLink(taskDTO);
        addStopLink(taskDTO);
        addWorkHoursLink(taskDTO);
    }

    private void addSelfLink(TaskDTO taskDTO){
       taskDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskResource.class)
               .getTask(taskDTO.getId()))
               .withSelfRel());
    }

    private void addAllTasksLink(TaskDTO taskDTO){
        taskDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskResource.class)
                .getAllTasks())
                .withRel("allTasks"));
    }

    private void addStartLink(TaskDTO taskDTO){
        taskDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskResource.class)
                .startTask(taskDTO.getId()))
                .withRel("startWork"));
    }

    private void addStopLink(TaskDTO taskDTO){
        taskDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskResource.class)
                .stopTask(taskDTO.getId()))
                .withRel("stopWork"));
    }

    private void addPriorityLink(TaskDTO taskDTO){
        taskDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskResource.class)
                .changePriority(taskDTO.getId(), null))
                .withRel("priority"));
    }

    private void addAssigneeLink(TaskDTO taskDTO){
        taskDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskResource.class)
                .assignTeammate(taskDTO.getId(), null))
                .withRel("assignee"));
    }

    private void addWorkHoursLink(TaskDTO taskDTO){
        taskDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskResource.class)
                .noteWorkHours(taskDTO.getId(), null))
                .withRel("workHours"));
    }

}
