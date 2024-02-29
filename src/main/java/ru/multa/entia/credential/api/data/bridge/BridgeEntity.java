package ru.multa.entia.credential.api.data.bridge;

import org.bson.types.ObjectId;

// TODO: impl
public interface BridgeEntity {
    void setId(ObjectId id);
    ObjectId getId();
    void setUserId(ObjectId userId);
    ObjectId getUserId();
    void setRightId(ObjectId rightId);
    ObjectId getRightId();
}
