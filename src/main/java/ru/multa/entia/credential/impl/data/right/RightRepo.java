package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RightRepo extends MongoRepository<RightEntityImpl, ObjectId> {
    RightEntityImpl findByValue(String value);
}
