package ru.agiletech.task.service.domain.timetracker;

import lombok.*;
import ru.agiletech.task.service.domain.supertype.Entity;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Optional;

@Getter
@Embeddable
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeTracker implements Entity {

    @Transient
    private static final long INITIAL_SPENT_HOURS = 0;
    @Transient
    private static final long ZERO_DAYS = 0;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="tracker_id"))
    })
    private TrackerId trackerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long      workHours;

    private TimeTracker(TrackerId trackerId) {
        this.trackerId  = trackerId;
    }

    public static TimeTracker create(){
        var trackerId = TrackerId.identifyTracker();

        return new TimeTracker(trackerId);
    }

    public void increaseWorkHours(long workHours){
        if(Optional.ofNullable(this.endDate).isEmpty())
            this.workHours +=workHours;
    }

    public void startTracking(){
        this.startDate = LocalDate.now();
        this.workHours = INITIAL_SPENT_HOURS;
    }

    public void stopTracking(){
        this.endDate = LocalDate.now();
    }

    public long calculateWorkingDays(){
        if(Optional.ofNullable(this.endDate).isPresent())
            return daysForEndDate();
        else
            return daysForHours();
    }

    private long daysForEndDate(){
        Period period = Period.between(this.startDate, this.endDate);

        return period.getDays();
    }

    private long daysForHours(){
        if(Optional.ofNullable(this.workHours).isPresent())
            return Duration.ofHours(this.workHours)
                    .toDays();

        return ZERO_DAYS;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        TimeTracker tracker = (TimeTracker) object;
        return Objects.equals(trackerId,
                tracker.trackerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackerId);
    }

}
