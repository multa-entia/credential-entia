package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.api.data.right.RightRepo;
import ru.multa.entia.credential.api.data.right.RightService;
import ru.multa.entia.results.api.result.Result;

import java.util.function.Function;

public class RightServiceImpl implements RightService {
    // TODO: !!!!
    /*

    public enum Code {
        TYPE_NOT_SET,
        PROPERTIES_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.TYPE_NOT_SET, "credential:properties.mongo.adapter:type-not-set");
        CR.update(Code.PROPERTIES_ABSENCE, "credential:properties.mongo.adapter:properties-absence");
    }

     */

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
//<
        throw new RuntimeException("getById");
    }

    @Override
    public Result<Right> findOneByValue(final String value) {
//<
        throw new RuntimeException("findOneByValue");
    }

    @Override
    public Result<Right> save(final Right right) {


//<
        throw new RuntimeException("save");
    }

    @Override
    public Result<Right> deleteById(final ObjectId id) {
        //<
        throw new RuntimeException("deleteById");
    }

    @Override
    public Result<Right> deleteByValue(final String value) {
        //<
        throw new RuntimeException("deleteByValue");
    }
}
