package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.usr.Usr;

import java.util.Objects;
import java.util.Set;

public record UsrImpl(ObjectId id,
                      String firstName,
                      String paterName,
                      String surname,
                      String email,
                      Set<Right> rights) implements Usr {

    public UsrImpl {
        firstName = Objects.requireNonNullElse(firstName, "");
        paterName = Objects.requireNonNullElse(paterName, "");
        surname = Objects.requireNonNullElse(surname, "");
        email = Objects.requireNonNullElse(email, "");
        rights = Objects.requireNonNullElse(rights, Set.of());
    }
}
