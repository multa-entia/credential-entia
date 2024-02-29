package ru.multa.entia.credential.impl.data.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.bridge.Bridge;

import static org.assertj.core.api.Assertions.assertThat;

class BridgeImplTest {

    @Test
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        BridgeImpl bridge = new BridgeImpl(expectedId, null, null);

        assertThat(bridge.id()).isEqualTo(expectedId);
    }

    @Test
    void shouldCheckUserIdGetting() {
        ObjectId expectedUserId = new ObjectId();
        BridgeImpl bridge = new BridgeImpl(null, expectedUserId, null);

        assertThat(bridge.userId()).isEqualTo(expectedUserId);
    }

    @Test
    void shouldCheckRightGetting() {
        ObjectId expectedRightId = new ObjectId();
        BridgeImpl bridge = new BridgeImpl(null, null, expectedRightId);

        assertThat(bridge.rightId()).isEqualTo(expectedRightId);
    }

    @Test
    void shouldCheckCreation() {
        ObjectId expectedUserId = new ObjectId();
        ObjectId expectedRightId = new ObjectId();
        Bridge bridge = BridgeImpl.create(expectedUserId, expectedRightId);

        assertThat(bridge).isEqualTo(new BridgeImpl(null, expectedUserId, expectedRightId));
    }
}