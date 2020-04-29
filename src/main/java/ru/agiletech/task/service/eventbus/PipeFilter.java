package ru.agiletech.task.service.eventbus;

public interface PipeFilter<T> {

    void onEvent(T serializedEvent);

}
