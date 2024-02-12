package ru.multa.entia.credential.api.data.usr;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsrRepo extends MongoRepository<UsrEntity, ObjectId> {
    List<UsrEntity> findByFirstName(String firstName);
    List<UsrEntity> findByPaterName(String paterName);
    List<UsrEntity> findBySurname(String surname);
    List<UsrEntity> findByEmail(String email);
}
