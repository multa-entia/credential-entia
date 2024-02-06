
// TODO: del
//package ru.multa.entia.credential.impl.config.mongo;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.connection.ClusterSettings;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import ru.multa.entia.fakers.impl.Faker;
//import ru.multa.entia.results.api.repository.CodeRepository;
//import ru.multa.entia.results.api.result.Result;
//import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
//import ru.multa.entia.results.utils.Results;
//
//import java.lang.reflect.Field;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class LocalMongoPropertiesOldTest {
//    private static final String EMPTY_ALIAS = "empty";
//    private static final String NULL_ALIAS = "null";
//    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
//
//
//    private static String prepareInput(final String input) {
//        if (EMPTY_ALIAS.equals(input)) {
//            return "";
//        } else if (NULL_ALIAS.equals(input)) {
//            return null;
//        }
//        return input;
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckSchemeSetting() {
//        String expectedScheme = Faker.str_().random();
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(expectedScheme);
//
//        Field field = properties.getClass().getDeclaredField("scheme");
//        field.setAccessible(true);
//        String gottenScheme = (String) field.get(properties);
//
//        assertThat(gottenScheme).isEqualTo(expectedScheme);
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckHostSetting() {
//        String expectedHost = Faker.str_().random();
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setHost(expectedHost);
//
//        Field field = properties.getClass().getDeclaredField("host");
//        field.setAccessible(true);
//        String gottenHost = (String) field.get(properties);
//
//        assertThat(gottenHost).isEqualTo(expectedHost);
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckPortSetting() {
//        String expectedPort = Faker.str_().random();
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setPort(expectedPort);
//
//        Field field = properties.getClass().getDeclaredField("port");
//        field.setAccessible(true);
//        String gottenHost = (String) field.get(properties);
//
//        assertThat(gottenHost).isEqualTo(expectedPort);
//    }
//
//    @SneakyThrows
//    @Test
//    void shouldCheckDatabaseNameSetting() {
//        String expectedDatabaseName = Faker.str_().random();
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setDatabaseName(expectedDatabaseName);
//
//        Field field = properties.getClass().getDeclaredField("databaseName");
//        field.setAccessible(true);
//        String gottenDatabaseName = (String) field.get(properties);
//
//        assertThat(gottenDatabaseName).isEqualTo(expectedDatabaseName);
//    }
//
//    @ParameterizedTest
//    @CsvSource(value = {
//            EMPTY_ALIAS,
//            NULL_ALIAS
//    })
//    void shouldCheckSettingsGetting_ifSchemeIsNotSet(String initScheme) {
//        initScheme = prepareInput(initScheme);
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(initScheme);
//
//        Result<MongoClientSettings> result = properties.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(LocalMongoPropertiesOld.Code.INVALID_SCHEME))
//                        .args(initScheme == null ? "null" : initScheme)
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @ParameterizedTest
//    @CsvSource(value = {
//            EMPTY_ALIAS,
//            NULL_ALIAS
//    })
//    void shouldCheckSettingsGetting_ifHostIsNotSet(String initHost) {
//        initHost = prepareInput(initHost);
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(Faker.str_().random());
//        properties.setHost(initHost);
//
//        Result<MongoClientSettings> result = properties.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(LocalMongoPropertiesOld.Code.INVALID_HOST))
//                        .args(initHost == null ? "null" : initHost)
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @ParameterizedTest
//    @CsvSource(value = {
//            EMPTY_ALIAS,
//            NULL_ALIAS
//    })
//    void shouldCheckSettingsGetting_ifPortIsNotSet(String initPort) {
//        initPort = prepareInput(initPort);
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(Faker.str_().random());
//        properties.setHost(Faker.str_().random());
//        properties.setPort(initPort);
//
//        Result<MongoClientSettings> result = properties.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(LocalMongoPropertiesOld.Code.PORT_NOT_SET))
//                        .args(initPort == null ? "null" : initPort)
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @ParameterizedTest
//    @CsvSource(value = {
//            "abc",
//            "-123",
//            "1_000_000"
//    })
//    void shouldCheckSettingsGetting_ifPortHasBadValue(String initPort) {
//        initPort = prepareInput(initPort);
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(Faker.str_().random());
//        properties.setHost(Faker.str_().random());
//        properties.setPort(initPort);
//
//        Result<MongoClientSettings> result = properties.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(LocalMongoPropertiesOld.Code.INVALID_PORT))
//                        .args(initPort == null ? "null" : initPort)
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @ParameterizedTest
//    @CsvSource(value = {
//            EMPTY_ALIAS,
//            NULL_ALIAS
//    })
//    void shouldCheckSettingsGetting_ifDatabaseNameIsNotSet(String intiDatabaseName) {
//        intiDatabaseName = prepareInput(intiDatabaseName);
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(Faker.str_().random());
//        properties.setHost(Faker.str_().random());
//        properties.setPort(String.valueOf(Faker.int_().between(0, 0xFFFF)));
//        properties.setDatabaseName(intiDatabaseName);
//
//        Result<MongoClientSettings> result = properties.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(LocalMongoPropertiesOld.Code.INVALID_DATABASE_NAME))
//                        .args(intiDatabaseName == null ? "null" : intiDatabaseName)
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @Test
//    void shouldCheckSettingsGetting_ifBadConnectionString() {
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(Faker.str_().random());
//        properties.setHost(Faker.str_().random());
//        properties.setPort(String.valueOf(Faker.int_().between(0, 0xFFFF)));
//        properties.setDatabaseName(Faker.str_().random());
//
//        Result<MongoClientSettings> result = properties.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(LocalMongoPropertiesOld.Code.INVALID_CONNECTION_STRING))
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @Test
//    void shouldCheckSettingsGetting() {
//        String scheme = "mongodb";
//        String host = "localhost";
//        String port = "27017";
//        String databaseName = "test";
//        String expected = String.format("%s://%s:%s/%s", scheme, host, port, databaseName);
//
//        LocalMongoPropertiesOld properties = new LocalMongoPropertiesOld();
//        properties.setScheme(scheme);
//        properties.setHost(host);
//        properties.setPort(port);
//        properties.setDatabaseName(databaseName);
//
//        Result<MongoClientSettings> result = properties.getSettings();
//
//        ConnectionString cs = new ConnectionString(expected);
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(cs)
//                .build();
//
//        assertThat(
//                Results.comparator(result)
//                        .isSuccess()
//                        .seedsComparator()
//                        .isNull()
//                        .back()
//                        .compare()
//        ).isTrue();
//
//        ClusterSettings clusterSettings = result.value().getClusterSettings();
//        assertThat(clusterSettings.getSrvServiceName()).isEqualTo(scheme);
//        assertThat(clusterSettings.getHosts()).hasSize(1);
//        assertThat(clusterSettings.getHosts().get(0).getHost()).isEqualTo(host);
//        assertThat(clusterSettings.getHosts().get(0).getPort()).isEqualTo(Integer.parseInt(port));
//    }
//}