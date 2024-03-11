package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.usr.Usr;

import java.util.Objects;

public record DefaultUsr(ObjectId id,
                         String firstName,
                         String paterName,
                         String surname,
                         String email) implements Usr {

    public DefaultUsr {
        firstName = Objects.requireNonNullElse(firstName, "");
        paterName = Objects.requireNonNullElse(paterName, "");
        surname = Objects.requireNonNullElse(surname, "");
        email = Objects.requireNonNullElse(email, "");
    }
}
