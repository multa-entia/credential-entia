package ru.multa.entia.credential.api.data.bridge;

import org.bson.types.ObjectId;

public interface Bridge {
    ObjectId id();
    ObjectId userId();
    ObjectId rightId();
}
