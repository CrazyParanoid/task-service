package ru.agiletech.task.service.input.ports.eventbus;

public interface PipeFilter<T> {

    void onEvent(T serializedEvent);

}
