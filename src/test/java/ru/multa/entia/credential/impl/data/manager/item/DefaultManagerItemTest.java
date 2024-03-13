package ru.multa.entia.credential.impl.data.manager.item;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultManagerItemTest {

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckBuilderPropertySetting() {
        Map<String, Object> expected = new HashMap<>();
        DefaultManagerItem.Builder builder = new DefaultManagerItem.Builder();

        int size = Faker.int_().between(5, 10);
        for (int i = 0; i < size; i++) {
            String key = Faker.str_().random();
            String value = Faker.str_().random();
            expected.put(key, value);
            builder.property(key, value);
        }

        Field field = builder.getClass().getDeclaredField("data");
        field.setAccessible(true);
        Map<String, Object> gottenMap = (Map<String, Object>) field.get(builder);

        assertThat(gottenMap).isEqualTo(expected);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckBuilding() {
        Map<String, Object> expected = new HashMap<>();
        DefaultManagerItem.Builder builder = new DefaultManagerItem.Builder();

        int size = Faker.int_().between(5, 10);
        for (int i = 0; i < size; i++) {
            String key = Faker.str_().random();
            String value = Faker.str_().random();
            expected.put(key, value);
            builder.property(key, value);
        }

        DefaultManagerItem item = builder.build();
        Field field = item.getClass().getDeclaredField("data");
        field.setAccessible(true);
        Map<String, Object> gottenMap = (Map<String, Object>) field.get(item);

        assertThat(gottenMap).isEqualTo(expected);
    }

    @Test
    void shouldCheckGetting_ifAbsence() {

    }

    @Test
    void shouldCheckGetting_ifBadType() {

    }

    @Test
    void shouldCheckGetting() {

    }
}