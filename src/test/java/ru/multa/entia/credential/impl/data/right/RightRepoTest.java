package ru.multa.entia.credential.impl.data.right;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
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
        RightEntityImpl entity = new RightEntityImpl();
        entity.setValue(expectedValue);

        RightEntityImpl saved = repo.save(entity);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getValue()).isEqualTo(expectedValue);

        Optional<RightEntityImpl> maybeGotten = repo.findById(saved.getId());
        assertThat(maybeGotten).isPresent();
        assertThat(maybeGotten.get().getId()).isEqualTo(saved.getId());
        assertThat(maybeGotten.get().getValue()).isEqualTo(expectedValue);
    }

    @Test
    void shouldCheckSaveAndFindByValue_ifAbsence() {
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl();
        entity.setValue(expectedValue);

        RightEntityImpl saved = repo.save(entity);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getValue()).isEqualTo(expectedValue);

        Optional<RightEntityImpl> maybeGotten = repo.findByValue(expectedValue + Faker.str_().random());
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

        Optional<RightEntityImpl> maybeGotten = repo.findByValue(expectedValue);
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
        Optional<RightEntityImpl> maybeEntity = repo.findById(saved.getId());

        assertThat(maybeEntity).isEmpty();
    }
}