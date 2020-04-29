package ru.agiletech.task.service.domain.supertype;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DomainEvent {

    private Date    occurredOn;
    private String  name;

}
