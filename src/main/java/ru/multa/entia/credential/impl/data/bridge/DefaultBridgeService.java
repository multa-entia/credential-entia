package ru.multa.entia.credential.impl.data.bridge;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.api.data.bridge.BridgeEntity;
import ru.multa.entia.credential.api.data.bridge.BridgeRepo;
import ru.multa.entia.credential.api.data.bridge.BridgeService;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DefaultBridgeService implements BridgeService {
    public enum Code {
        ENTITY_BY_ID_ABSENCE,
        ENTITY_BY_USER_ID_ABSENCE,
        ENTITY_BY_RIGHT_ID_ABSENCE,
        ENTITY_BY_USER_AND_RIGHT_IDS_ABSENCE,
        FAIL_SAVING
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.ENTITY_BY_ID_ABSENCE, "credential:service.bridge:entity-by-id-is-absence");
        CR.update(Code.ENTITY_BY_USER_ID_ABSENCE, "credential:service.bridge:entity-by-user-id-is-absence");
        CR.update(Code.ENTITY_BY_RIGHT_ID_ABSENCE, "credential:service.bridge:entity-by-right-id-is-absence");
        CR.update(Code.ENTITY_BY_USER_AND_RIGHT_IDS_ABSENCE, "credential:service.bridge:entity-by-user-and-right-ids-is-absence");
        CR.update(Code.FAIL_SAVING, "credential:service.bridge:fail-saving");
    }

    private final BridgeRepo repo;
    private final Function<Bridge, BridgeEntity> toEntityConverter;
    private final Function<BridgeEntity, Bridge> toBridgeConverter;

    @Autowired
    public DefaultBridgeService(final BridgeRepo repo,
                                final Function<Bridge, BridgeEntity> toEntityConverter,
                                final Function<BridgeEntity, Bridge> toBridgeConverter) {
        this.repo = repo;
        this.toEntityConverter = toEntityConverter;
        this.toBridgeConverter = toBridgeConverter;
    }

    @Override
    public Result<Bridge> getById(final ObjectId id) {
        Optional<BridgeEntity> maybeEntity = repo.findById(id);
        return maybeEntity.isPresent()
                ? DefaultResultBuilder.<Bridge>ok(toBridgeConverter.apply(maybeEntity.get()))
                : DefaultResultBuilder.<Bridge>fail(CR.get(Code.ENTITY_BY_ID_ABSENCE));
    }

    @Override
    public Result<Bridge> save(final Bridge bridge) {
        try {
            BridgeEntity saved = repo.save(toEntityConverter.apply(bridge));
            return DefaultResultBuilder.<Bridge>ok(toBridgeConverter.apply(saved));
        } catch (Throwable ex) {
            return DefaultResultBuilder.<Bridge>fail(CR.get(Code.FAIL_SAVING));
        }
    }

    @Override
    public Result<List<Bridge>> getByUserId(final ObjectId userId) {
        List<BridgeEntity> entities = repo.findByUserId(userId);
        return entities.isEmpty()
                ? DefaultResultBuilder.<List<Bridge>>fail(CR.get(Code.ENTITY_BY_USER_ID_ABSENCE))
                : DefaultResultBuilder.<List<Bridge>>ok(entities.stream().map(toBridgeConverter).toList());
    }

    @Override
    public Result<List<Bridge>> getByRightId(final ObjectId rightId) {
        List<BridgeEntity> entities = repo.findByRightId(rightId);
        return entities.isEmpty()
                ? DefaultResultBuilder.<List<Bridge>>fail(CR.get(Code.ENTITY_BY_RIGHT_ID_ABSENCE))
                : DefaultResultBuilder.<List<Bridge>>ok(entities.stream().map(toBridgeConverter).toList());
    }

    @Override
    public Result<List<Bridge>> getByUserAndRightIds(final ObjectId userId, final ObjectId rightId) {
        List<BridgeEntity> entities = repo.findByUserIdAndRightId(userId, rightId);
        return entities.isEmpty()
                ? DefaultResultBuilder.<List<Bridge>>fail(CR.get(Code.ENTITY_BY_USER_AND_RIGHT_IDS_ABSENCE))
                : DefaultResultBuilder.<List<Bridge>>ok(entities.stream().map(toBridgeConverter).toList());
    }

    @Override
    public Result<Bridge> deleteById(final ObjectId id) {
        repo.deleteById(id);
        return DefaultResultBuilder.<Bridge>ok();
    }
}
