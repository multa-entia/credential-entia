package ru.multa.entia.credential.api.data.usr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.multa.entia.credential.impl.data.usr.UsrEntityImpl;
import ru.multa.entia.fakers.impl.Faker;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UsrRepoTest {

    @Autowired
    private UsrRepo repo;

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void shouldCheckSaveAndFindById() {
        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();

        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setFirstName(expectedFirstName);
        entity.setPaterName(expectedPaterName);
        entity.setSurname(expectedSurname);
        entity.setEmail(expectedEmail);

        UsrEntityImpl saved = repo.save(entity);

        assertThat(saved.getId()).isNotNull();

        Optional<UsrEntity> maybeEntity = repo.findById(saved.getId());
        assertThat(maybeEntity).isPresent();
        UsrEntity gottenEntity = maybeEntity.get();
        assertThat(gottenEntity.getId()).isEqualTo(saved.getId());
        assertThat(gottenEntity.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(gottenEntity.getPaterName()).isEqualTo(expectedPaterName);
        assertThat(gottenEntity.getSurname()).isEqualTo(expectedSurname);
        assertThat(gottenEntity.getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    void shouldCheckFindByFirstName_ifAbsence() {
        String expectedFirstName = Faker.str_().random();
        List<UsrEntity> gottenEntities = repo.findByFirstName(expectedFirstName);

        assertThat(gottenEntities).isEmpty();
    }

    @Test
    void shouldCheckFindByFirstName() {
        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();

        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setFirstName(expectedFirstName);
        entity.setPaterName(expectedPaterName);
        entity.setSurname(expectedSurname);
        entity.setEmail(expectedEmail);

        UsrEntityImpl saved = repo.save(entity);

        List<UsrEntity> gottenEntities = repo.findByFirstName(expectedFirstName);
        assertThat(gottenEntities).hasSize(1);

        UsrEntity gottenEntity = gottenEntities.get(0);
        assertThat(gottenEntity.getId()).isEqualTo(saved.getId());
        assertThat(gottenEntity.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(gottenEntity.getPaterName()).isEqualTo(expectedPaterName);
        assertThat(gottenEntity.getSurname()).isEqualTo(expectedSurname);
        assertThat(gottenEntity.getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    void shouldCheckFindByPaterName_ifAbsence() {
        String expectedPaterName = Faker.str_().random();
        List<UsrEntity> gottenEntities = repo.findByPaterName(expectedPaterName);

        assertThat(gottenEntities).isEmpty();
    }

    @Test
    void shouldCheckFindByPaterName() {
        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();

        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setFirstName(expectedFirstName);
        entity.setPaterName(expectedPaterName);
        entity.setSurname(expectedSurname);
        entity.setEmail(expectedEmail);

        UsrEntityImpl saved = repo.save(entity);

        List<UsrEntity> gottenEntities = repo.findByPaterName(expectedPaterName);
        assertThat(gottenEntities).hasSize(1);

        UsrEntity gottenEntity = gottenEntities.get(0);
        assertThat(gottenEntity.getId()).isEqualTo(saved.getId());
        assertThat(gottenEntity.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(gottenEntity.getPaterName()).isEqualTo(expectedPaterName);
        assertThat(gottenEntity.getSurname()).isEqualTo(expectedSurname);
        assertThat(gottenEntity.getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    void shouldCheckFindBySurname_ifAbsence() {
        String expectedSurname = Faker.str_().random();
        List<UsrEntity> gottenEntities = repo.findBySurname(expectedSurname);

        assertThat(gottenEntities).isEmpty();
    }

    @Test
    void shouldCheckFindBySurname() {
        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();

        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setFirstName(expectedFirstName);
        entity.setPaterName(expectedPaterName);
        entity.setSurname(expectedSurname);
        entity.setEmail(expectedEmail);

        UsrEntityImpl saved = repo.save(entity);

        List<UsrEntity> gottenEntities = repo.findBySurname(expectedSurname);
        assertThat(gottenEntities).hasSize(1);

        UsrEntity gottenEntity = gottenEntities.get(0);
        assertThat(gottenEntity.getId()).isEqualTo(saved.getId());
        assertThat(gottenEntity.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(gottenEntity.getPaterName()).isEqualTo(expectedPaterName);
        assertThat(gottenEntity.getSurname()).isEqualTo(expectedSurname);
        assertThat(gottenEntity.getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    void shouldCheckFindByEmail_ifAbsence() {
        String expectedEmail = Faker.str_().random();
        List<UsrEntity> gottenEntities = repo.findByEmail(expectedEmail);

        assertThat(gottenEntities).isEmpty();
    }

    @Test
    void shouldCheckFindByEmail() {
        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();

        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setFirstName(expectedFirstName);
        entity.setPaterName(expectedPaterName);
        entity.setSurname(expectedSurname);
        entity.setEmail(expectedEmail);

        UsrEntityImpl saved = repo.save(entity);

        List<UsrEntity> gottenEntities = repo.findByEmail(expectedEmail);
        assertThat(gottenEntities).hasSize(1);

        UsrEntity gottenEntity = gottenEntities.get(0);
        assertThat(gottenEntity.getId()).isEqualTo(saved.getId());
        assertThat(gottenEntity.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(gottenEntity.getPaterName()).isEqualTo(expectedPaterName);
        assertThat(gottenEntity.getSurname()).isEqualTo(expectedSurname);
        assertThat(gottenEntity.getEmail()).isEqualTo(expectedEmail);
    }
}
