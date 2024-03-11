package ru.multa.entia.credential.api.data.bridge;

import org.bson.types.ObjectId;
import ru.multa.entia.results.api.result.Result;

import java.util.List;

public interface BridgeService {
    Result<Bridge> getById(ObjectId id);
    Result<Bridge> save(Bridge bridge);
    Result<List<Bridge>> getByUserId(ObjectId userId);
    Result<List<Bridge>> getByRightId(ObjectId rightId);
    Result<List<Bridge>> getByUserAndRightIds(ObjectId userId, ObjectId rightId);
    Result<Bridge> deleteById(ObjectId id);
}
