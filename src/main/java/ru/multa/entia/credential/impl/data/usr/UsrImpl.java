package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.usr.Usr;

import java.util.Objects;

// TODO: rename to Default...
public record UsrImpl(ObjectId id,
                      String firstName,
                      String paterName,
                      String surname,
                      String email) implements Usr {

    public UsrImpl {
        firstName = Objects.requireNonNullElse(firstName, "");
        paterName = Objects.requireNonNullElse(paterName, "");
        surname = Objects.requireNonNullElse(surname, "");
        email = Objects.requireNonNullElse(email, "");
    }
}
