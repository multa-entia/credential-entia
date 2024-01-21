package ru.multa.entia.credential.impl.converter.right;

import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.impl.data.right.RightImpl;

import java.util.function.Function;

@Component
public class DefaultEntity2RightConverter implements Function<RightEntity, Right> {
    @Override
    public Right apply(final RightEntity rightEntity) {
        return new RightImpl(rightEntity.getId(), rightEntity.getValue());
    }
}
