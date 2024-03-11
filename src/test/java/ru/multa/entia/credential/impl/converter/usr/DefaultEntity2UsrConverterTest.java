package ru.multa.entia.credential.impl.converter.usr;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.impl.data.usr.DefaultUsrEntity;
import ru.multa.entia.fakers.impl.Faker;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultEntity2UsrConverterTest {

    @Test
    void shouldCheckConversion() {
        ObjectId expectedId = new ObjectId();
        String expectedFirstName = Faker.str_().random();
        String expectedPaterName = Faker.str_().random();
        String expectedSurname = Faker.str_().random();
        String expectedEmail = Faker.str_().random();

        DefaultUsrEntity entity = new DefaultUsrEntity();
        entity.setId(expectedId);
        entity.setFirstName(expectedFirstName);
        entity.setPaterName(expectedPaterName);
        entity.setSurname(expectedSurname);
        entity.setEmail(expectedEmail);

        Usr usr = new DefaultEntity2UsrConverter().apply(entity);

        assertThat(usr.id()).isEqualTo(expectedId);
        assertThat(usr.firstName()).isEqualTo(expectedFirstName);
        assertThat(usr.paterName()).isEqualTo(expectedPaterName);
        assertThat(usr.surname()).isEqualTo(expectedSurname);
        assertThat(usr.email()).isEqualTo(expectedEmail);
    }
}