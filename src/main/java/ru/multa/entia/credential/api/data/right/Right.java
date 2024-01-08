package ru.multa.entia.credential.api.data.right;

import org.bson.types.ObjectId;

public interface Right {
    ObjectId id();
    String value();
}
