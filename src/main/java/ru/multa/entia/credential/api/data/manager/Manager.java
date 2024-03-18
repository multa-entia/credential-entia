package ru.multa.entia.credential.api.data.manager;

import ru.multa.entia.credential.api.data.manager.strategy.ManagerStrategy;
import ru.multa.entia.results.api.result.Result;

// TODO: impl
public interface Manager {
    Result<Object> start();
    Result<Object> stop();
    Result<Object> offer(ManagerStrategy strategy);
}
