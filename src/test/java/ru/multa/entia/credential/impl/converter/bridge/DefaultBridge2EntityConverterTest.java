package ru.multa.entia.credential.impl.converter.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.bridge.BridgeEntity;
import ru.multa.entia.credential.impl.data.bridge.BridgeImpl;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBridge2EntityConverterTest {

    @Test
    void shouldCheckConversion() {
        DefaultBridge2EntityConverter converter = new DefaultBridge2EntityConverter();
        ObjectId expectedId = new ObjectId();
        ObjectId expectedUserId = new ObjectId();
        ObjectId expectedRightId = new ObjectId();
        BridgeEntity gottenEntity = converter.apply(new BridgeImpl(expectedId, expectedUserId, expectedRightId));

        assertThat(gottenEntity.getId()).isEqualTo(expectedId);
        assertThat(gottenEntity.getUserId()).isEqualTo(expectedUserId);
        assertThat(gottenEntity.getRightId()).isEqualTo(expectedRightId);
    }
}