package ru.agiletech.task.service.domain.sprint;

import lombok.*;
import ru.agiletech.task.service.domain.supertype.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintId implements ValueObject {

    private String id;

    public static SprintId identifySprintFrom(String id){
        return new SprintId(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        SprintId sprintId = (SprintId) object;
        return Objects.equals(id,
                sprintId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
