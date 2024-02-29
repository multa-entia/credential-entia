package ru.multa.entia.credential.impl.data.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BridgeEntityImplTest {

    @Test
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        BridgeEntityImpl entity = new BridgeEntityImpl(expectedId, null, null);

        assertThat(entity.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldCheckIdSettingGetting() {
        ObjectId expectedId = new ObjectId();
        BridgeEntityImpl entity = new BridgeEntityImpl();
        entity.setId(expectedId);

        assertThat(entity.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldCheckUserIdGetting() {
        ObjectId expectedUserId = new ObjectId();
        BridgeEntityImpl entity = new BridgeEntityImpl(null, expectedUserId, null);

        assertThat(entity.getUserId()).isEqualTo(expectedUserId);
    }

    @Test
    void shouldCheckUserIdSetting() {
        ObjectId expectedUserId = new ObjectId();
        BridgeEntityImpl entity = new BridgeEntityImpl();
        entity.setUserId(expectedUserId);

        assertThat(entity.getUserId()).isEqualTo(expectedUserId);
    }

    @Test
    void shouldCheckRightIdGetting() {
        ObjectId expectedRightId = new ObjectId();
        BridgeEntityImpl entity = new BridgeEntityImpl(null, null, expectedRightId);

        assertThat(entity.getRightId()).isEqualTo(expectedRightId);
    }

    @Test
    void shouldCheckRightIdSetting() {
        ObjectId expectedRightId = new ObjectId();
        BridgeEntityImpl entity = new BridgeEntityImpl();
        entity.setRightId(expectedRightId);

        assertThat(entity.getRightId()).isEqualTo(expectedRightId);
    }
}