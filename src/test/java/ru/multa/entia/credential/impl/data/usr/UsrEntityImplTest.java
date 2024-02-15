package ru.multa.entia.credential.impl.data.usr;

import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.RepeatedTest;
import ru.multa.entia.fakers.impl.Faker;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class UsrEntityImplTest {

    @SneakyThrows
    @RepeatedTest(100)
    void shouldCheckIdSetting() {
        ObjectId expectedId = new ObjectId();
        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setId(expectedId);

        Field field = entity.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ObjectId gottenId = (ObjectId) field.get(entity);

        assertThat(gottenId).isEqualTo(expectedId);
    }

    @RepeatedTest(100)
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        UsrEntityImpl entity = new UsrEntityImpl(expectedId, null, null, null, null);

        assertThat(entity.getId()).isEqualTo(expectedId);
    }

    @SneakyThrows
    @RepeatedTest(100)
    void shouldCheckFirstNameSetting() {
        String expectedFirstName = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setFirstName(expectedFirstName);

        Field field = entity.getClass().getDeclaredField("firstName");
        field.setAccessible(true);
        String gottenFirstName = (String) field.get(entity);

        assertThat(gottenFirstName).isEqualTo(expectedFirstName);
    }

    @RepeatedTest(100)
    void shouldCheckFirstNameGetting() {
        String expectedFirstName = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl(null, expectedFirstName, null, null, null);

        assertThat(entity.getFirstName()).isEqualTo(expectedFirstName);
    }

    @SneakyThrows
    @RepeatedTest(100)
    void shouldCheckPaterNameSetting() {
        String expectedPaterName = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setPaterName(expectedPaterName);

        Field field = entity.getClass().getDeclaredField("paterName");
        field.setAccessible(true);
        String gottenPaterName = (String) field.get(entity);

        assertThat(gottenPaterName).isEqualTo(expectedPaterName);
    }

    @RepeatedTest(100)
    void shouldCheckPaterNameGetting() {
        String expectedPaterName = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl(null, null, expectedPaterName, null, null);

        assertThat(entity.getPaterName()).isEqualTo(expectedPaterName);
    }

    @SneakyThrows
    @RepeatedTest(100)
    void shouldCheckSurnameSetting() {
        String expectedSurname = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setSurname(expectedSurname);

        Field field = entity.getClass().getDeclaredField("surname");
        field.setAccessible(true);
        String gottenSurname = (String) field.get(entity);

        assertThat(gottenSurname).isEqualTo(expectedSurname);
    }

    @RepeatedTest(100)
    void shouldCheckSurnameGetting() {
        String expectedSurname = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl(null, null, null, expectedSurname, null);

        assertThat(entity.getSurname()).isEqualTo(expectedSurname);
    }

    @SneakyThrows
    @RepeatedTest(100)
    void shouldCheckEmailSetting() {
        String expectedEmail = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl();
        entity.setEmail(expectedEmail);

        Field field = entity.getClass().getDeclaredField("email");
        field.setAccessible(true);
        String gottenEmail = (String) field.get(entity);

        assertThat(gottenEmail).isEqualTo(expectedEmail);
    }

    @RepeatedTest(100)
    void shouldCheckEmailGetting() {
        String expectedEmail = Faker.str_().random();
        UsrEntityImpl entity = new UsrEntityImpl(null, null, null, null, expectedEmail);

        assertThat(entity.getEmail()).isEqualTo(expectedEmail);
    }
}