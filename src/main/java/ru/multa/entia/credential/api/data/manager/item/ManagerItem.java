package ru.multa.entia.credential.api.data.manager.item;

import ru.multa.entia.results.api.result.Result;


public interface ManagerItem {
    <T> Result<T> get(String property, Class<T> type);
}
