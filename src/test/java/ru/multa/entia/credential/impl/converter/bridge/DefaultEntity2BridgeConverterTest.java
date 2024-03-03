package ru.multa.entia.credential.impl.converter.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.impl.data.bridge.BridgeEntityImpl;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultEntity2BridgeConverterTest {

    @Test
    void shouldCheckConversion() {
        ObjectId expectedId = new ObjectId();
        ObjectId expectedUserId = new ObjectId();
        ObjectId expectedRightId = new ObjectId();
        DefaultEntity2BridgeConverter converter = new DefaultEntity2BridgeConverter();
        Bridge bridge = converter.apply(new BridgeEntityImpl(expectedId, expectedUserId, expectedRightId));

        assertThat(bridge.id()).isEqualTo(expectedId);
        assertThat(bridge.userId()).isEqualTo(expectedUserId);
        assertThat(bridge.rightId()).isEqualTo(expectedRightId);
    }
}