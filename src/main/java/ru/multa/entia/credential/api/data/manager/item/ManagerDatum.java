package ru.multa.entia.credential.api.data.manager.item;

import ru.multa.entia.results.api.result.Result;


public interface ManagerDatum {
    <T> Result<T> get(String property, Class<T> type);
    Result<Object> get(String property);
}
