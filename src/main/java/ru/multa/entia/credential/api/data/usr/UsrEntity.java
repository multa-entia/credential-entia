package ru.multa.entia.credential.api.data.usr;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.right.Right;

import java.util.Objects;
import java.util.Set;

public interface UsrEntity {
    void setId(ObjectId id);
    ObjectId getId();
    void setFirstName(String firstName);
    String getFirstName();
    void setPaterName(String paterName);
    String getPaterName();
    void setSurname(String surname);
    String getSurname();
    void setEmail(String email);
    String getEmail();
    void setRights(Set<Right> rights);
    Set<Right> getRights();
}
