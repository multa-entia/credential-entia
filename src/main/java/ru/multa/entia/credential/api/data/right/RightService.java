package ru.multa.entia.credential.api.data.right;

import org.bson.types.ObjectId;
import ru.multa.entia.results.api.result.Result;

public interface RightService {
    Result<Right> getById(ObjectId id);
    Result<Right> getOneByValue(String value);
    Result<Right> save(Right right);
    Result<Right> deleteById(ObjectId id);
    Result<Right> deleteByValue(String value);
}
