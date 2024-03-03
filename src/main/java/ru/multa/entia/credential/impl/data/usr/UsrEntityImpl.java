package ru.multa.entia.credential.impl.data.usr;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.multa.entia.credential.api.data.usr.UsrEntity;

// TODO: rename to Default...
@Document
@Data
public class UsrEntityImpl implements UsrEntity {
    @Id
    private ObjectId id;
    private String firstName;
    private String paterName;
    private String surname;
    private String email;

    public UsrEntityImpl() {
    }

    public UsrEntityImpl(final ObjectId id,
                         final String firstName,
                         final String paterName,
                         final String surname,
                         final String email) {
        this.id = id;
        this.firstName = firstName;
        this.paterName = paterName;
        this.surname = surname;
        this.email = email;
    }
}
