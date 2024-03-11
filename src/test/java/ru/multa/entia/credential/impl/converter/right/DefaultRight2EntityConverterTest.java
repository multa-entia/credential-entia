package ru.multa.entia.credential.impl.converter.right;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.impl.data.right.DefaultRight;
import ru.multa.entia.fakers.impl.Faker;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultRight2EntityConverterTest {

    @Test
    void shouldCheckConversion() {
        String expectedValue = Faker.str_().random();
        ObjectId expectedId = new ObjectId();
        Right right = new DefaultRight(expectedId, expectedValue);
        RightEntity entity = new DefaultRight2EntityConverter().apply(right);

        assertThat(entity.getId()).isEqualTo(expectedId);
        assertThat(entity.getValue()).isEqualTo(expectedValue);
    }

    @Test
    void shouldCheckConversion_ofNewRight() {
        String expectedValue = Faker.str_().random();
        Right right = DefaultRight.create(expectedValue);
        RightEntity entity = new DefaultRight2EntityConverter().apply(right);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getValue()).isEqualTo(expectedValue);
    }
}