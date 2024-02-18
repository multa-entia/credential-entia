package ru.multa.entia.credential.impl.converter.usr;


import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.usr.UsrEntity;
import ru.multa.entia.credential.impl.data.usr.UsrImpl;
import ru.multa.entia.fakers.impl.Faker;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultUsr2EntityConverterTest {

    @Test
    void shouldCheckConversion() {
        ObjectId expectedId = new ObjectId();
        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();
        UsrImpl usr = new UsrImpl(
                expectedId,
                expectedFirstName,
                expectedPaterName,
                expectedSurname,
                expectedEmail
        );

        UsrEntity entity = new DefaultUsr2EntityConverter().apply(usr);

        assertThat(entity.getId()).isEqualTo(expectedId);
        assertThat(entity.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(entity.getPaterName()).isEqualTo(expectedPaterName);
        assertThat(entity.getSurname()).isEqualTo(expectedSurname);
        assertThat(entity.getEmail()).isEqualTo(expectedEmail);
    }
}