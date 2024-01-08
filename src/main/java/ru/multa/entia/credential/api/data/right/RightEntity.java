package ru.multa.entia.credential.api.data.right;

import org.bson.types.ObjectId;

public interface RightEntity {
    void setId(ObjectId id);
    ObjectId getId();
    void setValue(String value);
    String getValue();
}
