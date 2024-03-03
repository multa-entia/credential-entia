package ru.multa.entia.credential.impl.converter.bridge;

import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.api.data.bridge.BridgeEntity;
import ru.multa.entia.credential.impl.data.bridge.BridgeEntityImpl;

import java.util.function.Function;

@Component
public class DefaultBridge2EntityConverter implements Function<Bridge, BridgeEntity> {
    @Override
    public BridgeEntity apply(final Bridge bridge) {
        return new BridgeEntityImpl(bridge.id(), bridge.userId(), bridge.rightId());
    }
}
