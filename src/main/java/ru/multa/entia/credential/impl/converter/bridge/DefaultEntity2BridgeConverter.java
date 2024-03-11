package ru.multa.entia.credential.impl.converter.bridge;

import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.api.data.bridge.BridgeEntity;
import ru.multa.entia.credential.impl.data.bridge.DefaultBridge;

import java.util.function.Function;

@Component
public class DefaultEntity2BridgeConverter implements Function<BridgeEntity, Bridge> {
    @Override
    public Bridge apply(final BridgeEntity bridgeEntity) {
        return new DefaultBridge(bridgeEntity.getId(), bridgeEntity.getUserId(), bridgeEntity.getRightId());
    }
}
