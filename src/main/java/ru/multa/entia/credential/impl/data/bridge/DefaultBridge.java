package ru.multa.entia.credential.impl.data.bridge;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.bridge.Bridge;

public record DefaultBridge(ObjectId id, ObjectId userId, ObjectId rightId) implements Bridge {
    public static Bridge create(final ObjectId userId, final ObjectId rightId) {
        return new DefaultBridge(null, userId, rightId);
    }
}
