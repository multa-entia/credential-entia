package ru.multa.entia.credential.impl.data.right;

import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.RepeatedTest;
import ru.multa.entia.fakers.impl.Faker;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class RightEntityImplTest {

    // TODO: restore
//    @SneakyThrows
//    @RepeatedTest(100)
//    void shouldCheckIdSetting() {
//        ObjectId expectedId = new ObjectId();
//        RightEntityImpl entity = new RightEntityImpl();
//        entity.setId(expectedId);
//
//        Field field = entity.getClass().getDeclaredField("id");
//        field.setAccessible(true);
//        ObjectId gottenId = (ObjectId) field.get(entity);
//
//        assertThat(gottenId).isEqualTo(expectedId);
//    }
//
//    @RepeatedTest(100)
//    void shouldCheckIdGetting() {
//        ObjectId expectedId = new ObjectId();
//        RightEntityImpl entity = new RightEntityImpl(expectedId, null);
//
//        assertThat(entity.getId()).isEqualTo(expectedId);
//    }
//
//    @SneakyThrows
//    @RepeatedTest(100)
//    void shouldCheckValueSetting() {
//        String expectedValue = Faker.str_().random();
//        RightEntityImpl entity = new RightEntityImpl();
//        entity.setValue(expectedValue);
//
//        Field field = entity.getClass().getDeclaredField("value");
//        field.setAccessible(true);
//        String gottenValue = (String) field.get(entity);
//
//        assertThat(gottenValue).isEqualTo(expectedValue);
//    }
//
//    @RepeatedTest(100)
//    void shouldCheckValueGetting() {
//        String expectedValue = Faker.str_().random();
//        RightEntityImpl entity = new RightEntityImpl(null, expectedValue);
//
//        assertThat(entity.getValue()).isEqualTo(expectedValue);
//    }
}