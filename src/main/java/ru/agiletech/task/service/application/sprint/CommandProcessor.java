package ru.agiletech.task.service.application.sprint;

public interface CommandProcessor<T extends Command> {

    void process(T command);

}
