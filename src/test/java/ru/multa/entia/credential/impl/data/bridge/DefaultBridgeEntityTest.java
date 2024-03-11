package ru.multa.entia.credential.impl.data.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBridgeEntityTest {

    @Test
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        DefaultBridgeEntity entity = new DefaultBridgeEntity(expectedId, null, null);

        assertThat(entity.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldCheckIdSettingGetting() {
        ObjectId expectedId = new ObjectId();
        DefaultBridgeEntity entity = new DefaultBridgeEntity();
        entity.setId(expectedId);

        assertThat(entity.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldCheckUserIdGetting() {
        ObjectId expectedUserId = new ObjectId();
        DefaultBridgeEntity entity = new DefaultBridgeEntity(null, expectedUserId, null);

        assertThat(entity.getUserId()).isEqualTo(expectedUserId);
    }

    @Test
    void shouldCheckUserIdSetting() {
        ObjectId expectedUserId = new ObjectId();
        DefaultBridgeEntity entity = new DefaultBridgeEntity();
        entity.setUserId(expectedUserId);

        assertThat(entity.getUserId()).isEqualTo(expectedUserId);
    }

    @Test
    void shouldCheckRightIdGetting() {
        ObjectId expectedRightId = new ObjectId();
        DefaultBridgeEntity entity = new DefaultBridgeEntity(null, null, expectedRightId);

        assertThat(entity.getRightId()).isEqualTo(expectedRightId);
    }

    @Test
    void shouldCheckRightIdSetting() {
        ObjectId expectedRightId = new ObjectId();
        DefaultBridgeEntity entity = new DefaultBridgeEntity();
        entity.setRightId(expectedRightId);

        assertThat(entity.getRightId()).isEqualTo(expectedRightId);
    }
}