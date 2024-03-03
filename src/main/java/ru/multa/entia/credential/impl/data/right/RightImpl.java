package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.right.Right;

// TODO: rename to Default...
public record RightImpl(ObjectId id, String value) implements Right {
    public static Right create(final String value) {
        return new RightImpl(null, value);
    };
}
