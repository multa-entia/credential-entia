package ru.multa.entia.credential.impl.data.usr;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrRepo;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.results.api.result.Result;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsrServiceImpl implements UsrService {
    private final UsrRepo repo;

    @Override
    public Result<Usr> getById(final ObjectId id) {
        return null;
    }

    @Override
    public Result<Usr> save(final Usr user) {
        return null;
    }

    @Override
    public Result<List<Usr>> findByFirstName(final String firstName) {
        return null;
    }

    @Override
    public Result<List<Usr>> findByPaterName(final String paterName) {
        return null;
    }

    @Override
    public Result<List<Usr>> findBySurname(final String surname) {
        return null;
    }

    @Override
    public Result<List<Usr>> findByEmail(final String email) {
        return null;
    }

    @Override
    public Result<Usr> deleteById(final ObjectId id) {
        return null;
    }
}
