package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.api.data.right.RightRepo;
import ru.multa.entia.credential.api.data.right.RightService;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.Optional;
import java.util.function.Function;

// TODO: rename to Default...
@Service
public class RightServiceImpl implements RightService {
    public enum Code {
        ENTITY_BY_ID_ABSENCE,
        ENTITY_BY_VALUE_ABSENCE,
        FAIL_SAVING
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.ENTITY_BY_ID_ABSENCE, "credential:service.right:entity-by-id-is-absence");
        CR.update(Code.ENTITY_BY_VALUE_ABSENCE, "credential:service.right:entity-by-value-is-absence");
        CR.update(Code.FAIL_SAVING, "credential:service.right:fail-saving");
    }

    private final RightRepo repo;
    private final Function<Right, RightEntity> toEntityConverter;
    private final Function<RightEntity, Right> toRightConverter;

    @Autowired
    public RightServiceImpl(final RightRepo repo,
                            final Function<Right, RightEntity> toEntityConverter,
                            final Function<RightEntity, Right> toRightConverter) {
        this.repo = repo;
        this.toEntityConverter = toEntityConverter;
        this.toRightConverter = toRightConverter;
    }

    @Override
    public Result<Right> getById(final ObjectId id) {
        Optional<RightEntity> maybeEntity = repo.findById(id);
        return maybeEntity.isPresent()
                ? DefaultResultBuilder.<Right>ok(toRightConverter.apply(maybeEntity.get()))
                : DefaultResultBuilder.<Right>fail(CR.get(Code.ENTITY_BY_ID_ABSENCE), id);
    }

    @Override
    public Result<Right> getOneByValue(final String value) {
        Optional<RightEntity> maybeEntity = repo.findByValue(value);
        return maybeEntity.isPresent()
                ? DefaultResultBuilder.<Right>ok(toRightConverter.apply(maybeEntity.get()))
                : DefaultResultBuilder.<Right>fail(CR.get(Code.ENTITY_BY_VALUE_ABSENCE), value);
    }

    @Override
    public Result<Right> save(final Right right) {
        try {
            RightEntity saved = repo.save(toEntityConverter.apply(right));
            return DefaultResultBuilder.<Right>ok(toRightConverter.apply(saved));
        } catch (Throwable ex) {
            return DefaultResultBuilder.<Right>fail(CR.get(Code.FAIL_SAVING));
        }
    }

    @Override
    public Result<Right> deleteById(final ObjectId id) {
        repo.deleteById(id);
        return DefaultResultBuilder.<Right>ok();
    }

    @Override
    public Result<Right> deleteByValue(final String value) {
        Optional<RightEntity> maybeEntity = repo.deleteByValue(value);
        return DefaultResultBuilder.<Right>ok(maybeEntity.map(toRightConverter).orElse(null));
    }
}
