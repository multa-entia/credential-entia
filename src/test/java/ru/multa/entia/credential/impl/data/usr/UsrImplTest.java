package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UsrImplTest {

    @Test
    void shouldCheckIdGetting() {
        ObjectId expectedId = new ObjectId();
        UsrImpl usr = new UsrImpl(expectedId, null, null, null, null);

        assertThat(usr.id()).isEqualTo(expectedId);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-first-name,some-first-name",
            "null,''"
    }, nullValues = "null")
    void shouldCheckFirstNameSettingGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), initValue, null, null, null);

        assertThat(usr.firstName()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-pater-name,some-pater-name",
            "null,''"
    }, nullValues = "null")
    void shouldCheckPaterNameGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), null, initValue, null, null);

        assertThat(usr.paterName()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-surname,some-surname",
            "null,''"
    }, nullValues = "null")
    void shouldCheckSurnameGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), null, null, initValue, null);

        assertThat(usr.surname()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "some-email,some-email",
            "null,''"
    }, nullValues = "null")
    void shouldCheckEmailGetting(String initValue, String expected) {
        UsrImpl usr = new UsrImpl(new ObjectId(), null, null, null, initValue);

        assertThat(usr.email()).isEqualTo(expected);
    }
}