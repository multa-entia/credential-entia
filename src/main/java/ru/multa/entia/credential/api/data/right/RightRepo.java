package ru.multa.entia.credential.api.data.right;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.multa.entia.credential.impl.data.right.RightEntityImpl;

import java.util.Optional;

public interface RightRepo extends MongoRepository<RightEntityImpl, ObjectId> {
    Optional<RightEntityImpl> findByValue(String value);
}
