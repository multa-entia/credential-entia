package ru.multa.entia.credential.impl.data.usr;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.usr.UsrEntity;

import java.util.Set;

@Document
@Data
public class UsrEntityImpl implements UsrEntity {
    @Id
    private ObjectId id;
    private String firstName;
    private String paterName;
    private String surname;
    private String email;
    private Set<Right> rights;

    public UsrEntityImpl() {
    }

    public UsrEntityImpl(final ObjectId id,
                         final String firstName,
                         final String paterName,
                         final String surname,
                         final String email,
                         final Set<Right> rights) {
        this.id = id;
        this.firstName = firstName;
        this.paterName = paterName;
        this.surname = surname;
        this.email = email;
        this.rights = rights;
    }
}
