package ru.multa.entia.credential.impl.converter.right;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.impl.data.right.RightEntityImpl;
import ru.multa.entia.fakers.impl.Faker;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultEntity2RightConverterTest {

    @Test
    void shouldCheckConversion() {
        ObjectId expectedId = new ObjectId();
        String expectedValue = Faker.str_().random();
        RightEntityImpl entity = new RightEntityImpl(expectedId, expectedValue);

        Right right = new DefaultEntity2RightConverter().apply(entity);

        assertThat(right.id()).isEqualTo(entity.getId());
        assertThat(right.value()).isEqualTo(entity.getValue());
    }
}