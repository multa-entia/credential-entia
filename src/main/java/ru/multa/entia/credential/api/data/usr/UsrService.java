package ru.multa.entia.credential.api.data.usr;

import org.bson.types.ObjectId;
import ru.multa.entia.results.api.result.Result;

import java.util.List;

public interface UsrService {
    Result<Usr> getById(ObjectId id);
    Result<Usr> save(Usr user);
    Result<List<Usr>> getByFirstName(String firstName);
    Result<List<Usr>> getByPaterName(String paterName);
    Result<List<Usr>> getBySurname(String surname);
    Result<List<Usr>> getByEmail(String email);
    Result<Usr> deleteById(ObjectId id);
}
