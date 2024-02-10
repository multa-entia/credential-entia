package ru.multa.entia.credential.api.data.usr;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.right.Right;

import java.util.Set;

public interface Usr {
    ObjectId id();
    String firstName();
    String paterName();
    String surname();
    String email();
    Set<Right> rights();
}
