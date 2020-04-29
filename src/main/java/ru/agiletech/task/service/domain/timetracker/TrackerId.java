package ru.agiletech.task.service.domain.timetracker;

import lombok.*;
import ru.agiletech.task.service.domain.supertype.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackerId implements ValueObject {

    private String id;

    public static TrackerId identifyTracker(){
        String id = UUID.randomUUID().toString();

        return new TrackerId(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        TrackerId trackerId = (TrackerId) object;
        return Objects.equals(id,
                trackerId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
