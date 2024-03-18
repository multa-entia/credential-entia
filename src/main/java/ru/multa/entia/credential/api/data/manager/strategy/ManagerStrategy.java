package ru.multa.entia.credential.api.data.manager.strategy;

import ru.multa.entia.credential.api.data.manager.item.ManagerItem;
import ru.multa.entia.results.api.result.Result;

// TODO: ???
public interface ManagerStrategy {
    Result<ManagerItem> execute(ManagerItem item);

    /*
    Result<Usr> getById(ObjectId id);
    Result<Usr> save(Usr user);
    Result<List<Usr>> getByFirstName(String firstName);
    Result<List<Usr>> getByPaterName(String paterName);
    Result<List<Usr>> getBySurname(String surname);
    Result<List<Usr>> getByEmail(String email);
    Result<Usr> deleteById(ObjectId id);
* */
    /*
    Result<Right> getById(ObjectId id);
    Result<Right> getOneByValue(String value);
    Result<Right> save(Right right);
    Result<Right> deleteById(ObjectId id);
    Result<Right> deleteByValue(String value);
     */
    /*
    Result<Bridge> getById(ObjectId id);
    Result<Bridge> save(Bridge bridge);
    Result<List<Bridge>> getByUserId(ObjectId userId);
    Result<List<Bridge>> getByRightId(ObjectId rightId);
    Result<List<Bridge>> getByUserAndRightIds(ObjectId userId, ObjectId rightId);
    Result<Bridge> deleteById(ObjectId id);
     */
}
