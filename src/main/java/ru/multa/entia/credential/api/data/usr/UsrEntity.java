package ru.multa.entia.credential.api.data.usr;

import org.bson.types.ObjectId;

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
}
