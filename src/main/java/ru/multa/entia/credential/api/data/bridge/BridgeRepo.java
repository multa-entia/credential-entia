package ru.multa.entia.credential.api.data.bridge;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// TODO: test
public interface BridgeRepo extends MongoRepository<BridgeEntity, ObjectId> {
    List<BridgeEntity> findByUserId(ObjectId userId);
    List<BridgeEntity> findByRightId(ObjectId rightId);
    List<BridgeEntity> findByUserIdAndRightId(ObjectId userId, ObjectId rightId);
}
