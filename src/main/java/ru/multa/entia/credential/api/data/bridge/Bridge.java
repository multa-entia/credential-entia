package ru.multa.entia.credential.api.data.bridge;

import org.bson.types.ObjectId;

// TODO: impl
public interface Bridge {
    ObjectId id();
    ObjectId userId();
    ObjectId rightId();
}
