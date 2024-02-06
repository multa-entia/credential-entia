
// TODO: del
//package ru.multa.entia.credential.impl.config.mongo;
//
//import com.mongodb.MongoClientSettings;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import ru.multa.entia.credential.api.config.mongo.MongoProperties;
//import ru.multa.entia.results.api.repository.CodeRepository;
//import ru.multa.entia.results.api.result.Result;
//import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
//import ru.multa.entia.results.impl.result.DefaultResultBuilder;
//import ru.multa.entia.results.utils.Results;
//
//import java.lang.reflect.Field;
//import java.util.EnumMap;
//import java.util.function.Supplier;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class MongoPropertiesAdapterTest {
//
//    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
//
//    @SneakyThrows
//    @Test
//    void shouldCheckTypeSetting() {
//        PropertiesSetType expectedType = PropertiesSetType.LOCAL;
//        MongoPropertiesAdapter adapter = new MongoPropertiesAdapter();
//        adapter.setType(expectedType);
//
//        Field field = adapter.getClass().getDeclaredField("type");
//        field.setAccessible(true);
//        PropertiesSetType type = (PropertiesSetType) field.get(adapter);
//
//        assertThat(type).isEqualTo(expectedType);
//    }
//
//    @SuppressWarnings("unchecked")
//    @SneakyThrows
//    @Test
//    void shouldCheckPropertyRegister() {
//        PropertiesSetType expectedType = PropertiesSetType.LOCAL;
//        MongoProperties expectedProperties = Mockito.mock(MongoProperties.class);
//
//        EnumMap<PropertiesSetType, MongoProperties> expected = new EnumMap<>(PropertiesSetType.class){{
//            put(expectedType, expectedProperties);
//        }};
//
//        MongoPropertiesAdapter adapter = new MongoPropertiesAdapter();
//        adapter.register(expectedType, expectedProperties);
//
//        Field field = adapter.getClass().getDeclaredField("properties");
//        field.setAccessible(true);
//        EnumMap<PropertiesSetType, MongoProperties> gottenProperties
//                = (EnumMap<PropertiesSetType, MongoProperties>) field.get(adapter);
//
//        assertThat(gottenProperties).isEqualTo(expected);
//    }
//
//    @Test
//    void shouldCheckSettingGetting_ifTypeNotSet() {
//        MongoPropertiesAdapter adapter = new MongoPropertiesAdapter();
//        Result<MongoClientSettings> result = adapter.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(MongoPropertiesAdapter.Code.TYPE_NOT_SET))
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @Test
//    void shouldCheckSettingGetting_ifPropertiesByTypeAbsence() {
//        MongoPropertiesAdapter adapter = new MongoPropertiesAdapter();
//        adapter.setType(PropertiesSetType.LOCAL);
//        Result<MongoClientSettings> result = adapter.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isFail()
//                        .value(null)
//                        .seedsComparator()
//                        .code(CR.get(MongoPropertiesAdapter.Code.PROPERTIES_ABSENCE))
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//
//    @Test
//    void shouldCheckSettingGetting() {
//        MongoClientSettings expectedSettings = Mockito.mock(MongoClientSettings.class);
//
//        PropertiesSetType expectedType = PropertiesSetType.LOCAL;
//        Supplier<MongoProperties> mongoPropertiesSupplier = () -> {
//            MongoProperties properties = Mockito.mock(MongoProperties.class);
//            Result<MongoClientSettings> result = DefaultResultBuilder.<MongoClientSettings>ok(expectedSettings);
//            Mockito
//                    .when(properties.getSettings())
//                    .thenReturn(result);
//
//            return properties;
//        };
//
//        MongoPropertiesAdapter adapter = new MongoPropertiesAdapter();
//        adapter.setType(expectedType);
//        adapter.register(expectedType, mongoPropertiesSupplier.get());
//
//        Result<MongoClientSettings> result = adapter.getSettings();
//
//        assertThat(
//                Results.comparator(result)
//                        .isSuccess()
//                        .value(expectedSettings)
//                        .seedsComparator()
//                        .isNull()
//                        .back()
//                        .compare()
//        ).isTrue();
//    }
//}