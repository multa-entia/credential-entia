package ru.multa.entia.credential.api.data.right;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import ru.multa.entia.credential.impl.data.right.DefaultRightEntity;
import ru.multa.entia.fakers.impl.Faker;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@ActiveProfiles("test")
class RightRepoTest {

    @Autowired
    private RightRepo repo;

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void shouldCheckSaveAndFindById() {
        String expectedValue = Faker.str_().random();
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setValue(expectedValue);

        DefaultRightEntity saved = repo.save(entity);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getValue()).isEqualTo(expectedValue);

        Optional<RightEntity> maybeGotten = repo.findById(saved.getId());
        assertThat(maybeGotten).isPresent();
        assertThat(maybeGotten.get().getId()).isEqualTo(saved.getId());
        assertThat(maybeGotten.get().getValue()).isEqualTo(expectedValue);
    }

    @Test
    void shouldCheckSaveAndFindByValue_ifAbsence() {
        String expectedValue = Faker.str_().random();
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setValue(expectedValue);

        DefaultRightEntity saved = repo.save(entity);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getValue()).isEqualTo(expectedValue);

        Optional<RightEntity> maybeGotten = repo.findByValue(expectedValue + Faker.str_().random());
        assertThat(maybeGotten).isEmpty();
    }

    @Test
    void shouldCheckSaveAndFindByValue() {
        String expectedValue = Faker.str_().random();
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setValue(expectedValue);

        DefaultRightEntity saved = repo.save(entity);
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
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setValue(expectedValue);
        repo.save(entity);

        Throwable throwable = catchThrowable(() -> {
            DefaultRightEntity newEntity = new DefaultRightEntity();
            newEntity.setValue(expectedValue);
            repo.save(newEntity);
        });
        assertThat(throwable).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void shouldCheckDelete() {
        String expectedValue = Faker.str_().random();
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setValue(expectedValue);

        DefaultRightEntity saved = repo.save(entity);
        repo.deleteById(saved.getId());
        Optional<RightEntity> maybeEntity = repo.findById(saved.getId());

        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldCheckDeleteByValue() {
        String expectedValue = Faker.str_().random();
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setValue(expectedValue);

        DefaultRightEntity saved = repo.save(entity);
        repo.deleteByValue(expectedValue);
        Optional<RightEntity> maybeEntity = repo.findById(saved.getId());

        assertThat(maybeEntity).isEmpty();
    }
}