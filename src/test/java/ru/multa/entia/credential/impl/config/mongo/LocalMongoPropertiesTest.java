package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.connection.ClusterSettings;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;
import ru.multa.entia.results.utils.Results;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class LocalMongoPropertiesTest {
    private static final String EMPTY_ALIAS = "empty";
    private static final String NULL_ALIAS = "null";
    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();


    private static String prepareInput(final String input) {
        if (EMPTY_ALIAS.equals(input)) {
            return "";
        } else if (NULL_ALIAS.equals(input)) {
            return null;
        }
        return input;
    }

    @SneakyThrows
    @Test
    void shouldCheckSchemeSetting() {
        String expectedScheme = Faker.str_().random();
        LocalMongoProperties properties = new LocalMongoProperties(expectedScheme, null, null, null);

        Field field = properties.getClass().getDeclaredField("scheme");
        field.setAccessible(true);
        String gottenScheme = (String) field.get(properties);

        assertThat(gottenScheme).isEqualTo(expectedScheme);
    }

    @SneakyThrows
    @Test
    void shouldCheckHostSetting() {
        String expectedHost = Faker.str_().random();
        LocalMongoProperties properties = new LocalMongoProperties(null, expectedHost, null, null);

        Field field = properties.getClass().getDeclaredField("host");
        field.setAccessible(true);
        String gottenHost = (String) field.get(properties);

        assertThat(gottenHost).isEqualTo(expectedHost);
    }

    @SneakyThrows
    @Test
    void shouldCheckPortSetting() {
        String expectedPort = Faker.str_().random();
        LocalMongoProperties properties = new LocalMongoProperties(null, null, expectedPort, null);

        Field field = properties.getClass().getDeclaredField("port");
        field.setAccessible(true);
        String gottenHost = (String) field.get(properties);

        assertThat(gottenHost).isEqualTo(expectedPort);
    }

    @SneakyThrows
    @Test
    void shouldCheckDatabaseNameSetting() {
        String expectedDatabaseName = Faker.str_().random();
        LocalMongoProperties properties = new LocalMongoProperties(null, null, null, expectedDatabaseName);

        Field field = properties.getClass().getDeclaredField("databaseName");
        field.setAccessible(true);
        String gottenDatabaseName = (String) field.get(properties);

        assertThat(gottenDatabaseName).isEqualTo(expectedDatabaseName);
    }

    @ParameterizedTest
    @CsvSource(value = {
            EMPTY_ALIAS,
            NULL_ALIAS
    })
    void shouldCheckSettingsGetting_ifSchemeIsNotSet(String initScheme) {
        initScheme = prepareInput(initScheme);
        LocalMongoProperties properties = new LocalMongoProperties(initScheme, null, null, null);

        Result<MongoClientSettings> result = properties.getSettings(DefaultResultBuilder::ok);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(LocalMongoProperties.Code.INVALID_SCHEME))
                        .args(initScheme == null ? "null" : initScheme)
                        .back()
                        .compare()
        ).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
            EMPTY_ALIAS,
            NULL_ALIAS
    })
    void shouldCheckSettingsGetting_ifHostIsNotSet(String initHost) {
        initHost = prepareInput(initHost);
        LocalMongoProperties properties = new LocalMongoProperties(Faker.str_().random(), initHost, null, null);

        Result<MongoClientSettings> result = properties.getSettings(DefaultResultBuilder::ok);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(LocalMongoProperties.Code.INVALID_HOST))
                        .args(initHost == null ? "null" : initHost)
                        .back()
                        .compare()
        ).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
            EMPTY_ALIAS,
            NULL_ALIAS
    })
    void shouldCheckSettingsGetting_ifPortIsNotSet(String initPort) {
        initPort = prepareInput(initPort);
        LocalMongoProperties properties = new LocalMongoProperties(Faker.str_().random(), Faker.str_().random(), initPort, null);

        Result<MongoClientSettings> result = properties.getSettings(DefaultResultBuilder::ok);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(LocalMongoProperties.Code.PORT_NOT_SET))
                        .args(initPort == null ? "null" : initPort)
                        .back()
                        .compare()
        ).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "abc",
            "-123",
            "1_000_000"
    })
    void shouldCheckSettingsGetting_ifPortHasBadValue(String initPort) {
        initPort = prepareInput(initPort);
        LocalMongoProperties properties = new LocalMongoProperties(Faker.str_().random(), Faker.str_().random(), initPort, null);

        Result<MongoClientSettings> result = properties.getSettings(DefaultResultBuilder::ok);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(LocalMongoProperties.Code.INVALID_PORT))
                        .args(initPort == null ? "null" : initPort)
                        .back()
                        .compare()
        ).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
            EMPTY_ALIAS,
            NULL_ALIAS
    })
    void shouldCheckSettingsGetting_ifDatabaseNameIsNotSet(String intiDatabaseName) {
        intiDatabaseName = prepareInput(intiDatabaseName);
        LocalMongoProperties properties = new LocalMongoProperties(Faker.str_().random(), Faker.str_().random(), String.valueOf(Faker.int_().between(0, 0xFFFF)), intiDatabaseName);

        Result<MongoClientSettings> result = properties.getSettings(DefaultResultBuilder::ok);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(LocalMongoProperties.Code.INVALID_DATABASE_NAME))
                        .args(intiDatabaseName == null ? "null" : intiDatabaseName)
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckSettingsGetting_ifBadConnectionString() {
        LocalMongoProperties properties = new LocalMongoProperties(Faker.str_().random(), Faker.str_().random(), String.valueOf(Faker.int_().between(0, 0xFFFF)), Faker.str_().random());

        Result<MongoClientSettings> result = properties.getSettings(DefaultResultBuilder::ok);

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(LocalMongoProperties.Code.INVALID_CONNECTION_STRING))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckSettingsGetting() {
        String scheme = "mongodb";
        String host = "localhost";
        String port = "27017";
        String databaseName = "test";
        String expected = String.format("%s://%s:%s/%s", scheme, host, port, databaseName);

        LocalMongoProperties properties = new LocalMongoProperties(scheme, host, port, databaseName);

        Result<MongoClientSettings> result = properties.getSettings(DefaultResultBuilder::ok);

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        ClusterSettings clusterSettings = result.value().getClusterSettings();
        assertThat(clusterSettings.getSrvServiceName()).isEqualTo(scheme);
        assertThat(clusterSettings.getHosts()).hasSize(1);
        assertThat(clusterSettings.getHosts().get(0).getHost()).isEqualTo(host);
        assertThat(clusterSettings.getHosts().get(0).getPort()).isEqualTo(Integer.parseInt(port));
    }
}