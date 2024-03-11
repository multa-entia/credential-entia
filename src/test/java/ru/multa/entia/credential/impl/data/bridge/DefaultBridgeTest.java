package ru.multa.entia.credential.impl.data.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.bridge.Bridge;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBridgeTest {

    @Test
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        DefaultBridge bridge = new DefaultBridge(expectedId, null, null);

        assertThat(bridge.id()).isEqualTo(expectedId);
    }

    @Test
    void shouldCheckUserIdGetting() {
        ObjectId expectedUserId = new ObjectId();
        DefaultBridge bridge = new DefaultBridge(null, expectedUserId, null);

        assertThat(bridge.userId()).isEqualTo(expectedUserId);
    }

    @Test
    void shouldCheckRightGetting() {
        ObjectId expectedRightId = new ObjectId();
        DefaultBridge bridge = new DefaultBridge(null, null, expectedRightId);

        assertThat(bridge.rightId()).isEqualTo(expectedRightId);
    }

    @Test
    void shouldCheckCreation() {
        ObjectId expectedUserId = new ObjectId();
        ObjectId expectedRightId = new ObjectId();
        Bridge bridge = DefaultBridge.create(expectedUserId, expectedRightId);

        assertThat(bridge).isEqualTo(new DefaultBridge(null, expectedUserId, expectedRightId));
    }
}