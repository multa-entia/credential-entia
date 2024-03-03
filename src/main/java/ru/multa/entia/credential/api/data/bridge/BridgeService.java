package ru.multa.entia.credential.api.data.bridge;

import org.bson.types.ObjectId;
import ru.multa.entia.results.api.result.Result;

public interface BridgeService {
    Result<Bridge> getById(ObjectId id);
    Result<Bridge> getByUserId(ObjectId userId);
    Result<Bridge> getByRightId(ObjectId rightId);
    Result<Bridge> getByUserAndRightIds(ObjectId userId, ObjectId rightId);
    Result<Bridge> deleteById();
}
