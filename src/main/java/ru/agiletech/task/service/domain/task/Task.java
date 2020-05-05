package ru.agiletech.task.service.domain.task;

import lombok.*;
import ru.agiletech.task.service.domain.supertype.AggregateRoot;
import ru.agiletech.task.service.domain.timetracker.TimeTracker;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Task extends AggregateRoot {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="task_id"))
    })
    private TaskId          taskId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="key", column=@Column(name="project_key"))
    })
    private Project         project;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="assignee_id"))
    })
    private TeammateId      assignee;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="sprint_id"))
    })
    private SprintId        sprintId;

    @Embedded
    private TimeTracker     timeTracker;

    @Enumerated(EnumType.STRING)
    private Priority        priority;
    @Enumerated(EnumType.STRING)
    private WorkFlowStatus  workFlowStatus;

    private String          summary;
    private String          description;

    Task(TaskId             taskId,
         TimeTracker        timeTracker,
         Priority           priority,
         Project            project,
         WorkFlowStatus     workFlowStatus,
         String             summary,
         String             description) {
        this.taskId         = taskId;
        this.timeTracker    = timeTracker;
        this.priority       = priority;
        this.project        = project;
        this.workFlowStatus = workFlowStatus;
        this.summary        = summary;
        this.description    = description;
    }

    public void startWork(){
        if(this.workFlowStatus != WorkFlowStatus.TODO)
            throw new UnsupportedOperationException("Невозможно начать работу по задаче. " +
                    "Не назначен исполнитель");

        this.timeTracker.startTracking();
        this.workFlowStatus = WorkFlowStatus.IN_PROGRESS;
    }

    public void stopWork(){
        if(this.workFlowStatus != WorkFlowStatus.IN_PROGRESS)
            throw new UnsupportedOperationException("Невозможно остановить работу по задаче. " +
                    "Задача должна находиться в работе");

        this.timeTracker.stopTracking();
        this.workFlowStatus = WorkFlowStatus.DONE;
    }

    public void noteWorkHours(long workHours){
        if(this.workFlowStatus != WorkFlowStatus.IN_PROGRESS)
            throw new UnsupportedOperationException("Невозможно отметить затраченное время. " +
                    "Задача должна находиться в работе");

        this.timeTracker.increaseWorkHours(workHours);
    }

    public void assignTeammate(TeammateId teammateId){
        if(this.workFlowStatus != WorkFlowStatus.BACKLOG)
            throw new UnsupportedOperationException("Невозможно назначить исполнителя. " +
                    "Задача должна находиться в бэклоге");

        this.assignee = teammateId;
        this.workFlowStatus = WorkFlowStatus.TODO;
    }

    public void changePriority(Priority priority){
        if(priority == Priority.UNKNOWN)
            throw new IllegalArgumentException("Unknown priority");

        this.priority = priority;
    }

    public void scheduleToSprint(SprintId sprintId){
        this.sprintId = sprintId;
    }

    public String taskId(){
        return this.taskId.getId();
    }

    public TaskSnapshot makeSnapshot(){
        Long workHours = this.timeTracker.getWorkHours();
        Long workDays = this.timeTracker.calculateWorkingDays();

        return new TaskSnapshot(this.workFlowStatus,
                this.priority,
                this.sprintId,
                this.assignee,
                workHours,
                workDays,
                this.timeTracker.getStartDate(),
                this.timeTracker.getEndDate());
    }

    @RequiredArgsConstructor
    public enum WorkFlowStatus{
        BACKLOG("BACKLOG"),
        TODO("TODO"),
        IN_PROGRESS("IN_PROGRESS"),
        DONE("DONE"),
        UNKNOWN("UNKNOWN");

        @Getter
        private final String name;

        public static WorkFlowStatus fromName(String name){
            for(WorkFlowStatus workFlowStatus: WorkFlowStatus.values()){
                if(workFlowStatus.getName().equals(name))
                    return workFlowStatus;
            }

            return UNKNOWN;
        }

    }

    @RequiredArgsConstructor
    public enum Priority{
        LOW("LOW"),
        MEDIUM("MEDIUM"),
        HIGH("HIGH"),
        UNKNOWN("UNKNOWN");

        @Getter
        private final String name;

        public static Priority fromName(String name){
            for(Priority priority: Priority.values()){
                if(priority.getName().equals(name))
                    return priority;
            }

            return UNKNOWN;
        }

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null
                || getClass() != object.getClass())
            return false;
        Task task = (Task) object;
        return Objects.equals(taskId,
                task.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }

}
