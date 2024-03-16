package ru.multa.entia.credential.impl.data.manager.item;

import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.utils.Results;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultManagerItemTest {

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();

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
        Result<String> result = DefaultManagerItem.builder().build().get(Faker.str_().random(), String.class);

        assertThat(Results.comparator(result)
                .isFail()
                .value(null)
                .seedsComparator()
                .code(CR.get(DefaultManagerItem.Code.PROPERTY_IS_ABSENCE))
                .back()
                .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGetting_ifBadType() {
        String expectedKey = Faker.str_().random();
        String expectedValue = Faker.str_().random();

        Result<Float> result = DefaultManagerItem.builder()
                .property(expectedKey, expectedValue)
                .build()
                .get(expectedKey, Float.class);

        assertThat(Results.comparator(result)
                .isFail()
                .value(null)
                .seedsComparator()
                .code(CR.get(DefaultManagerItem.Code.PROPERTY_HAS_BAD_TYPE))
                .back()
                .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckGetting() {
        String expectedStringKey = Faker.str_().random();
        String expectedStringValue = Faker.str_().random();
        String expectedIntegerKey = Faker.str_().random();
        Integer expectedIntegerValue = Faker.int_().random();
        String expectedObjectIdKey = Faker.str_().random();
        ObjectId expectedObjectIdValue = new ObjectId();

        DefaultManagerItem item = DefaultManagerItem.builder()
                .property(expectedStringKey, expectedStringValue)
                .property(expectedIntegerKey, expectedIntegerValue)
                .property(expectedObjectIdKey, expectedObjectIdValue)
                .build();

        Result<String> sResult = item.get(expectedStringKey, String.class);
        assertThat(Results.comparator(sResult)
                .isSuccess()
                .value(expectedStringValue)
                .seedsComparator()
                .isNull()
                .back()
                .compare()
        ).isTrue();

        Result<Integer> iResult = item.get(expectedIntegerKey, Integer.class);
        assertThat(Results.comparator(iResult)
                .isSuccess()
                .value(expectedIntegerValue)
                .seedsComparator()
                .isNull()
                .back()
                .compare()
        ).isTrue();

        Result<ObjectId> oiResult = item.get(expectedObjectIdKey, ObjectId.class);
        assertThat(Results.comparator(oiResult)
                .isSuccess()
                .value(expectedObjectIdValue)
                .seedsComparator()
                .isNull()
                .back()
                .compare()
        ).isTrue();
    }
}