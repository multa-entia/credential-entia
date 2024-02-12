package ru.multa.entia.credential.api.data.right;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.usr.UsrEntity;
import ru.multa.entia.credential.impl.data.usr.UsrEntityImpl;

import java.util.Set;

public interface RightEntity {
    void setId(ObjectId id);
    ObjectId getId();
    void setValue(String value);
    String getValue();

    // TODO: !!!
    void setUsers(Set<UsrEntity> users);
    Set<UsrEntity> getUsers();
}
