package ru.multa.entia.credential.impl.data.bridge;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.multa.entia.credential.api.data.bridge.BridgeEntity;

@Document
@Data
public class BridgeEntityImpl implements BridgeEntity {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private ObjectId rightId;

    public BridgeEntityImpl() {
    }

    public BridgeEntityImpl(ObjectId id, ObjectId userId, ObjectId rightId) {
        this.id = id;
        this.userId = userId;
        this.rightId = rightId;
    }
}
