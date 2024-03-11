package ru.multa.entia.credential.impl.data.right;

import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.RepeatedTest;
import ru.multa.entia.fakers.impl.Faker;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultRightEntityTest {

    @SneakyThrows
    @RepeatedTest(100)
    void shouldCheckIdSetting() {
        ObjectId expectedId = new ObjectId();
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setId(expectedId);

        Field field = entity.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ObjectId gottenId = (ObjectId) field.get(entity);

        assertThat(gottenId).isEqualTo(expectedId);
    }

    @RepeatedTest(100)
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        DefaultRightEntity entity = new DefaultRightEntity(expectedId, null);

        assertThat(entity.getId()).isEqualTo(expectedId);
    }

    @SneakyThrows
    @RepeatedTest(100)
    void shouldCheckValueSetting() {
        String expectedValue = Faker.str_().random();
        DefaultRightEntity entity = new DefaultRightEntity();
        entity.setValue(expectedValue);

        Field field = entity.getClass().getDeclaredField("value");
        field.setAccessible(true);
        String gottenValue = (String) field.get(entity);

        assertThat(gottenValue).isEqualTo(expectedValue);
    }

    @RepeatedTest(100)
    void shouldCheckValueGetting() {
        String expectedValue = Faker.str_().random();
        DefaultRightEntity entity = new DefaultRightEntity(null, expectedValue);

        assertThat(entity.getValue()).isEqualTo(expectedValue);
    }
}