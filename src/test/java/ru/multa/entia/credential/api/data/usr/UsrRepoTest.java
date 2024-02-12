package ru.multa.entia.credential.api.data.usr;

import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.api.data.right.RightRepo;
import ru.multa.entia.credential.impl.data.right.RightEntityImpl;
import ru.multa.entia.credential.impl.data.usr.UsrEntityImpl;
import ru.multa.entia.fakers.impl.Faker;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UsrRepoTest {

    @Autowired
    private UsrRepo repo;
    @Autowired
    private RightRepo rightRepo;

    @Test
    void shouldCheckSaveAndFindById() {
        RightEntityImpl right0 = new RightEntityImpl();
        right0.setValue(Faker.str_().random());
        RightEntityImpl right1 = new RightEntityImpl();
        right1.setValue(Faker.str_().random());

        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();
        Set<RightEntity> expectedRights = Set.of(
                rightRepo.save(right0),
                rightRepo.save(right1)
        );

        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setFirstName(expectedFirstName);
        entity.setPaterName(expectedPaterName);
        entity.setSurname(expectedSurname);
        entity.setEmail(expectedEmail);
        entity.setRights(expectedRights);

        right0.setUsers(Set.of(entity));
//        rightRepo.save(right0);
        right1.setUsers(Set.of(entity));
//        rightRepo.save(right1);

        UsrEntityImpl saved = repo.save(entity);

//        right0.setUsers(Set.of(entity));
//        rightRepo.save(right0);
//        right1.setUsers(Set.of(entity));
//        rightRepo.save(right1);

        assertThat(saved.getId()).isNotNull();

        Optional<UsrEntity> maybeEntity = repo.findById(saved.getId());
        assertThat(maybeEntity).isPresent();
        UsrEntity gottenEntity = maybeEntity.get();
        assertThat(gottenEntity.getId()).isEqualTo(saved.getId());
        assertThat(gottenEntity.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(gottenEntity.getPaterName()).isEqualTo(expectedPaterName);
        assertThat(gottenEntity.getSurname()).isEqualTo(expectedSurname);
        assertThat(gottenEntity.getEmail()).isEqualTo(expectedEmail);
        assertThat(gottenEntity.getRights()).isEqualTo(expectedRights);
    }

    /*

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void shouldCheckSaveAndFindByValue_ifAbsence() {
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setValue(expectedValue);

        RightEntityImpl saved = repo.save(entity);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getValue()).isEqualTo(expectedValue);

        Optional<RightEntity> maybeGotten = repo.findByValue(expectedValue + Faker.str_().random());
        assertThat(maybeGotten).isEmpty();
    }

    @Test
    void shouldCheckSaveAndFindByValue() {
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setValue(expectedValue);

        RightEntityImpl saved = repo.save(entity);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getValue()).isEqualTo(expectedValue);

        Optional<RightEntity> maybeGotten = repo.findByValue(expectedValue);
        assertThat(maybeGotten).isPresent();
        assertThat(maybeGotten.get().getId()).isEqualTo(saved.getId());
        assertThat(maybeGotten.get().getValue()).isEqualTo(expectedValue);
    }

    @Test
    void shouldSaveWithOneValue() {
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setValue(expectedValue);
        repo.save(entity);

        Throwable throwable = catchThrowable(() -> {
            RightEntityImpl newEntity = new RightEntityImpl();
            newEntity.setValue(expectedValue);
            repo.save(newEntity);
        });
        assertThat(throwable).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void shouldCheckDelete() {
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setValue(expectedValue);

        RightEntityImpl saved = repo.save(entity);
        repo.deleteById(saved.getId());
        Optional<RightEntity> maybeEntity = repo.findById(saved.getId());

        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldCheckDeleteByValue() {
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setValue(expectedValue);

        RightEntityImpl saved = repo.save(entity);
        repo.deleteByValue(expectedValue);
        Optional<RightEntity> maybeEntity = repo.findById(saved.getId());

        assertThat(maybeEntity).isEmpty();
    }
}

     */
}
