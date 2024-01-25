package ru.multa.entia.credential.api.data.right;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RightRepo extends MongoRepository<RightEntity, ObjectId> {
    Optional<RightEntity> findByValue(String value);
    Optional<RightEntity> deleteByValue(String value);
}
