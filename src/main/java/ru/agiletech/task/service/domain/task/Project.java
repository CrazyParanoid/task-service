package ru.agiletech.task.service.domain.task;

import lombok.*;
import ru.agiletech.task.service.domain.supertype.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Project implements ValueObject {

    private String key;

    static Project createFrom(String key){
        return new Project(key);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        Project project = (Project) object;
        return Objects.equals(key,
                project.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

}
