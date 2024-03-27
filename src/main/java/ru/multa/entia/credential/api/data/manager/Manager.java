package ru.multa.entia.credential.api.data.manager;

import ru.multa.entia.credential.api.data.manager.command.ManagerCommand;
import ru.multa.entia.results.api.result.Result;

public interface Manager {
    Result<Object> start();
    Result<Object> stop();
    Result<Object> offer(ManagerCommand command);
}
