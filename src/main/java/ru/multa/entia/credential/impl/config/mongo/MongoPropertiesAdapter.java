package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.MongoClientSettings;
import lombok.Setter;
import ru.multa.entia.credential.api.config.mongo.MongoProperties;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.EnumMap;

class MongoPropertiesAdapter implements MongoProperties {
    public enum Code {
        TYPE_NOT_SET,
        PROPERTIES_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.TYPE_NOT_SET, "credential:properties.mongo.adapter:type-not-set");
        CR.update(Code.PROPERTIES_ABSENCE, "credential:properties.mongo.adapter:properties-absence");
    }

    private final EnumMap<PropertiesSetType, MongoProperties> properties = new EnumMap<>(PropertiesSetType.class);

    @Setter
    private PropertiesSetType type;

    @Override
    public Result<MongoClientSettings> getSettings() {
        if (type == null) {
            return DefaultResultBuilder.<MongoClientSettings>fail(CR.get(Code.TYPE_NOT_SET));
        }

        return properties.containsKey(type)
                ? properties.get(type).getSettings()
                : DefaultResultBuilder.<MongoClientSettings>fail(CR.get(Code.PROPERTIES_ABSENCE));
    }

    public void register(final PropertiesSetType type, final MongoProperties properties) {
        this.properties.put(type, properties);
    }
}
