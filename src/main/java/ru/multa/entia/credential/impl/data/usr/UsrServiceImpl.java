package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrEntity;
import ru.multa.entia.credential.api.data.usr.UsrRepo;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UsrServiceImpl implements UsrService {
    public enum Code {
        ENTITY_BY_ID_ABSENCE,
        ENTITIES_BY_FIRST_NAME_ABSENCE,
        ENTITIES_BY_PATER_NAME_ABSENCE,
        ENTITIES_BY_SURNAME_ABSENCE,
        ENTITIES_BY_EMAIL_ABSENCE,
        FAIL_SAVING
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.ENTITY_BY_ID_ABSENCE, "credential:service.usr:entity-by-id-is-absence");
        CR.update(Code.ENTITIES_BY_FIRST_NAME_ABSENCE, "credential:service.usr:entities-by-first-name-is-absence");
        CR.update(Code.ENTITIES_BY_PATER_NAME_ABSENCE, "credential:service.usr:entities-by-pater-name-is-absence");
        CR.update(Code.ENTITIES_BY_SURNAME_ABSENCE, "credential:service.usr:entities-by-surname-is-absence");
        CR.update(Code.ENTITIES_BY_EMAIL_ABSENCE, "credential:service.usr:entities-by-email-is-absence");
        CR.update(Code.FAIL_SAVING, "credential:service.usr:fail-saving");
    }

    private final UsrRepo repo;
    private final Function<Usr, UsrEntity> toEntityConverter;
    private final Function<UsrEntity, Usr> toUsrConverter;

    @Autowired
    public UsrServiceImpl(final UsrRepo repo,
                          final Function<Usr, UsrEntity> toEntityConverter,
                          final Function<UsrEntity, Usr> toUsrConverter) {
        this.repo = repo;
        this.toEntityConverter = toEntityConverter;
        this.toUsrConverter = toUsrConverter;
    }

    @Override
    public Result<Usr> getById(final ObjectId id) {
        Optional<UsrEntity> maybeEntity = repo.findById(id);
        return maybeEntity.isPresent()
                ? DefaultResultBuilder.<Usr>ok(toUsrConverter.apply(maybeEntity.get()))
                : DefaultResultBuilder.<Usr>fail(CR.get(Code.ENTITY_BY_ID_ABSENCE));
    }

    @Override
    public Result<Usr> save(final Usr user) {
        try {
            UsrEntity saved = repo.save(toEntityConverter.apply(user));
            return DefaultResultBuilder.<Usr>ok(toUsrConverter.apply(saved));
        } catch (Throwable ex) {
            return DefaultResultBuilder.<Usr>fail(CR.get(Code.FAIL_SAVING));
        }
    }

    @Override
    public Result<List<Usr>> findByFirstName(final String firstName) {
        List<UsrEntity> entities = repo.findByFirstName(firstName);
        return entities.isEmpty()
                ? createFailResult(Code.ENTITIES_BY_FIRST_NAME_ABSENCE)
                : createOkResult(entities);
    }

    @Override
    public Result<List<Usr>> findByPaterName(final String paterName) {
        List<UsrEntity> entities = repo.findByPaterName(paterName);
        return entities.isEmpty()
                ? createFailResult(Code.ENTITIES_BY_PATER_NAME_ABSENCE)
                : createOkResult(entities);
    }

    @Override
    public Result<List<Usr>> findBySurname(final String surname) {
        List<UsrEntity> entities = repo.findBySurname(surname);
        return entities.isEmpty()
                ? createFailResult(Code.ENTITIES_BY_SURNAME_ABSENCE)
                : createOkResult(entities);
    }

    @Override
    public Result<List<Usr>> findByEmail(final String email) {
        List<UsrEntity> entities = repo.findByEmail(email);
        return entities.isEmpty()
                ? createFailResult(Code.ENTITIES_BY_EMAIL_ABSENCE)
                : createOkResult(entities);
    }

    @Override
    public Result<Usr> deleteById(final ObjectId id) {
        repo.deleteById(id);
        return DefaultResultBuilder.<Usr>ok();
    }

    private Result<List<Usr>> createFailResult(final Code code) {
        return DefaultResultBuilder.<List<Usr>>fail(CR.get(code));
    }

    private Result<List<Usr>> createOkResult(final List<UsrEntity> entities) {
        return  DefaultResultBuilder.<List<Usr>>ok(entities.stream().map(toUsrConverter).toList());
    }
}
