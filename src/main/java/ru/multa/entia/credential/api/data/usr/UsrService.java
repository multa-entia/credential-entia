package ru.multa.entia.credential.api.data.usr;

import org.bson.types.ObjectId;
import ru.multa.entia.results.api.result.Result;

import java.util.List;

public interface UsrService {
    Result<Usr> getById(ObjectId id);
    Result<Usr> save(Usr user);
    Result<List<Usr>> findByFirstName(String firstName);
    Result<List<Usr>> findByPaterName(String paterName);
    Result<List<Usr>> findBySurname(String surname);
    Result<List<Usr>> findByEmail(String email);
    Result<Usr> deleteById(ObjectId id);
}
