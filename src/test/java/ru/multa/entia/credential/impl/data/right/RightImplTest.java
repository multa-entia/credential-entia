package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.RepeatedTest;
import ru.multa.entia.fakers.impl.Faker;

import static org.assertj.core.api.Assertions.assertThat;

class RightImplTest {

    @RepeatedTest(100)
    void shouldCheckObjectIdGetting() {
        ObjectId expectedId = new ObjectId();
        RightImpl right = new RightImpl(expectedId, null);

        assertThat(right.id()).isEqualTo(expectedId);
    }

    @RepeatedTest(100)
    void shouldCheckValueGetting() {
        String expectedValue = Faker.str_().random();
        RightImpl right = new RightImpl(null, expectedValue);

        assertThat(right.value()).isEqualTo(expectedValue);
    }
}