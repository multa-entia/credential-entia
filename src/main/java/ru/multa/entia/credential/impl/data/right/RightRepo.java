package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RightRepo extends MongoRepository<RightEntityImpl, ObjectId> {
    Optional<RightEntityImpl> findByValue(String value);
}
