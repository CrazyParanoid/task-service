package ru.agiletech.task.service.domain.task;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import ru.agiletech.task.service.domain.supertype.AggregateRoot;
import ru.agiletech.task.service.domain.timetracker.TimeTracker;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

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

    private Task(TaskId             taskId,
                 TimeTracker        timeTracker,
                 Priority           priority,
                 WorkFlowStatus     workFlowStatus,
                 String             summary,
                 String             description) {
        this.taskId         = taskId;
        this.timeTracker    = timeTracker;
        this.priority       = priority;
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
        if(this.workFlowStatus == WorkFlowStatus.DONE)
            throw new UnsupportedOperationException("Невозможно спланировать завершенную задачу в спринт.");

        this.sprintId = sprintId;
    }

    public String taskId(){
        return this.taskId.getId();
    }

    public String sprintId(){
        if(Optional.ofNullable(this.sprintId).isPresent())
            return this.sprintId.getId();

        return StringUtils.EMPTY;
    }

    public String assigneeId(){
        if(Optional.ofNullable(this.assignee).isPresent())
            return this.assignee.getId();

        return StringUtils.EMPTY;
    }

    public String workFlowStatus(){
        return this.workFlowStatus.getName();
    }

    public String priority(){
        return this.priority.getName();
    }

    public Long workHours(){
        return this.timeTracker.getWorkHours();
    }

    public Long workDays(){
        return this.timeTracker.calculateWorkingDays();
    }

    public LocalDate startDate(){
        return this.timeTracker.getStartDate();
    }

    public LocalDate endDate(){
        return this.timeTracker.getEndDate();
    }

    public static Task create(String summary,
                              String description){
        var taskId = TaskId.identifyTask();
        TimeTracker timeTracker = TimeTracker.create();

        return new Task(taskId,
                timeTracker,
                Priority.LOW,
                WorkFlowStatus.BACKLOG,
                summary,
                description);
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
