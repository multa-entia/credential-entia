package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.fakers.impl.Faker;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class UsrImplTest {

    @Test
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        UsrImpl usr = new UsrImpl(expectedId, null, null, null, null, null);

        assertThat(usr.id()).isEqualTo(expectedId);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-first-name,some-first-name",
            "null,''"
    }, nullValues = "null")
    void shouldCheckFirstNameSettingGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), initValue, null, null, null, null);

        assertThat(usr.firstName()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-pater-name,some-pater-name",
            "null,''"
    }, nullValues = "null")
    void shouldCheckPaterNameGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), null, initValue, null, null, null);

        assertThat(usr.paterName()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-surname,some-surname",
            "null,''"
    }, nullValues = "null")
    void shouldCheckSurnameGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), null, null, initValue, null, null);

        assertThat(usr.surname()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-email,some-email",
            "null,''"
    }, nullValues = "null")
    void shouldCheckEmailGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), null, null, null, initValue, null);

        assertThat(usr.email()).isEqualTo(expected);
    }

    @Test
    void shouldCheckRightsGetting_ifNull() {
        UsrImpl usr = new UsrImpl(new ObjectId(), null, null, null, null, null);

        assertThat(usr.rights()).isEmpty();
    }

    @Test
    void shouldCheckRightsGetting() {
        Function<String, Right> rightFunction = value -> {
            Right right = Mockito.mock(Right.class);
            Mockito.when(right.value()).thenReturn(value);

            return right;
        };

        Set<Right> expectedRights = Set.of(
                rightFunction.apply(Faker.str_().random()),
                rightFunction.apply(Faker.str_().random()),
                rightFunction.apply(Faker.str_().random())
        );

        UsrImpl usr = new UsrImpl(new ObjectId(), null, null, null, null, expectedRights);

        assertThat(usr.rights()).isEqualTo(expectedRights);
    }
}