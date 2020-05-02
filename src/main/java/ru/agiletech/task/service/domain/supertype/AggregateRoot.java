package ru.agiletech.task.service.domain.supertype;

import org.apache.commons.collections.CollectionUtils;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AggregateRoot extends IdentifiedDomainObject
        implements Entity{

    @Transient
    private List<DomainEvent> domainEvents;

    public void registerDomainEvent(DomainEvent domainEvent){
        if(CollectionUtils.isEmpty(this.domainEvents))
            this.domainEvents = new ArrayList<>();

        this.domainEvents.add(domainEvent);
    }

    public <T extends DomainEvent> List<T> getDomainEventsByType(Class<T> clazz){
        List<T> events = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(this.domainEvents))
            events = this.domainEvents.stream()
                    .filter(clazz::isInstance)
                    .map(clazz::cast)
                    .collect(Collectors.toList());

        return events;
    }

}
