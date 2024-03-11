package ru.multa.entia.credential.impl.converter.right;

import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.impl.data.right.DefaultRightEntity;

import java.util.function.Function;

@Component
public class DefaultRight2EntityConverter implements Function<Right, RightEntity> {
    @Override
    public RightEntity apply(final Right right) {
        return new DefaultRightEntity(right.id(), right.value());
    }
}
