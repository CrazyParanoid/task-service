package ru.agiletech.task.service.domain.task;

import ru.agiletech.task.service.domain.supertype.DomainEvent;

import java.util.List;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(List<T> domainEvents);

}
