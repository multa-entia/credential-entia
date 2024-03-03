package ru.multa.entia.credential.api.data.bridge;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.multa.entia.credential.impl.data.bridge.BridgeEntityImpl;
import ru.multa.entia.fakers.impl.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BridgeRepoTest {

    @Autowired
    private BridgeRepo repo;

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void shouldCheckSaveAndFindById() {
        ObjectId expectedUserId = new ObjectId();
        ObjectId expectedRightId = new ObjectId();

        BridgeEntityImpl entity = new BridgeEntityImpl(null, expectedUserId, expectedRightId);

        BridgeEntityImpl saved = repo.save(entity);
        assertThat(saved.getId()).isNotNull();

        Optional<BridgeEntity> maybeEntity = repo.findById(saved.getId());
        assertThat(maybeEntity).isPresent();

        BridgeEntity gottenEntity = maybeEntity.get();
        assertThat(gottenEntity.getId()).isEqualTo(saved.getId());
        assertThat(gottenEntity.getUserId()).isEqualTo(expectedUserId);
        assertThat(gottenEntity.getRightId()).isEqualTo(expectedRightId);
    }

    @Test
    void shouldCheckFindingByUserId_ifAbsence() {
        List<BridgeEntity> entities = repo.findByUserId(new ObjectId());
        assertThat(entities).isEmpty();
    }

    @Test
    void shouldCheckFindingByUserId() {
        Integer entitiesQuantity = Faker.int_().between(2, 10);
        ObjectId expectedUserId = new ObjectId();

        ArrayList<BridgeEntity> entitiesForSaving = new ArrayList<>();
        for (int i = 0; i < entitiesQuantity; i++) {
            BridgeEntityImpl entity = new BridgeEntityImpl(null, expectedUserId, new ObjectId());
            entitiesForSaving.add(entity);
            repo.save(entity);
        }

        List<BridgeEntity> gottenEntities = repo.findByUserId(expectedUserId);
        assertThat(gottenEntities).hasSize(entitiesQuantity);

        for (int i = 0; i < gottenEntities.size(); i++) {
            assertThat(gottenEntities.get(i).getId()).isNotNull();
            assertThat(gottenEntities.get(i).getUserId()).isEqualTo(entitiesForSaving.get(i).getUserId());
            assertThat(gottenEntities.get(i).getRightId()).isEqualTo(entitiesForSaving.get(i).getRightId());
        }
    }

    @Test
    void shouldCheckFindingByRightId_ifAbsence() {
        List<BridgeEntity> entities = repo.findByRightId(new ObjectId());
        assertThat(entities).isEmpty();
    }

    @Test
    void shouldCheckFindingByRightId() {
        Integer entitiesQuantity = Faker.int_().between(2, 10);
        ObjectId expectedRightId = new ObjectId();

        ArrayList<BridgeEntity> entitiesForSaving = new ArrayList<>();
        for (int i = 0; i < entitiesQuantity; i++) {
            BridgeEntityImpl entity = new BridgeEntityImpl(null, new ObjectId(), expectedRightId);
            entitiesForSaving.add(entity);
            repo.save(entity);
        }

        List<BridgeEntity> gottenEntities = repo.findByRightId(expectedRightId);
        assertThat(gottenEntities).hasSize(entitiesQuantity);

        for (int i = 0; i < gottenEntities.size(); i++) {
            assertThat(gottenEntities.get(i).getId()).isNotNull();
            assertThat(gottenEntities.get(i).getUserId()).isEqualTo(entitiesForSaving.get(i).getUserId());
            assertThat(gottenEntities.get(i).getRightId()).isEqualTo(entitiesForSaving.get(i).getRightId());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "false,false",
            "true,false",
            "false,true"
    })
    void shouldCheckFindingByUserAndRightIds_idAbsence(boolean userIdPresent, boolean rightIdPresent) {
        ObjectId userIdForSaving = new ObjectId();
        ObjectId rightIdForSaving = new ObjectId();

        ObjectId userIdForFinding = userIdPresent ? userIdForSaving : new ObjectId();
        ObjectId rightIdForFinding = rightIdPresent ? rightIdForSaving : new ObjectId();

        repo.save(new BridgeEntityImpl(null, userIdForSaving, rightIdForSaving));
        List<BridgeEntity> result = repo.findByUserIdAndRightId(userIdForFinding, rightIdForFinding);

        assertThat(result).isEmpty();
    }

    @RepeatedTest(10)
    void shouldCheckFindingByUserAndRightIds() {
        BridgeEntityImpl entity = new BridgeEntityImpl(null, new ObjectId(), new ObjectId());
        repo.save(entity);
        List<BridgeEntity> result = repo.findByUserIdAndRightId(entity.getUserId(), entity.getRightId());

        assertThat(result).hasSize(1);
        BridgeEntity gottenEntity = result.get(0);
        assertThat(gottenEntity.getId()).isNotNull();
        assertThat(gottenEntity.getUserId()).isEqualTo(entity.getUserId());
        assertThat(gottenEntity.getRightId()).isEqualTo(entity.getRightId());
    }
}
