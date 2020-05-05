package ru.agiletech.task.service.input.ports.eventbus;

public interface EventSubscriber<T> {

    void onEvent(T serializedEvent);

}
