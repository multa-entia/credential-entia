package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.MongoClientSettings;
import lombok.Setter;
import ru.multa.entia.credential.api.config.mongo.MongoProperties;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

@Setter
class LocalMongoProperties implements MongoProperties {
    public enum  Code {
        INVALID_SCHEME,
        INVALID_HOST,
        PORT_NOT_SET,
        INVALID_PORT,
        INVALID_DATABASE_NAME
    }

    public static final PropertiesSetType TYPE = PropertiesSetType.LOCAL;
    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.INVALID_SCHEME, "credential:properties.mongo.local:invalid-scheme");
        CR.update(Code.INVALID_HOST, "credential:properties.mongo.local:invalid-host");
        CR.update(Code.PORT_NOT_SET, "credential:properties.mongo.local:port-not-set");
        CR.update(Code.INVALID_PORT, "credential:properties.mongo.local:invalid-port");
        CR.update(Code.INVALID_DATABASE_NAME, "credential:properties.mongo.local:invalid-database-name");
    }

    private String scheme;
    private String host;
    private String port;
    private String databaseName;

    // TODO: refactor
    @Override
    public Result<MongoClientSettings> getSettings() {

        if (scheme == null || scheme.isBlank()) {
            return DefaultResultBuilder.<MongoClientSettings>fail(
                    CR.get(Code.INVALID_SCHEME),
                    scheme == null ? "null" : scheme
            );
        }

        if (host == null || host.isBlank()) {
            return DefaultResultBuilder.<MongoClientSettings>fail(
                    CR.get(Code.INVALID_HOST),
                    host == null ? "null" : host
            );
        }

        if (port == null || port.isBlank()) {
            return DefaultResultBuilder.<MongoClientSettings>fail(
                    CR.get(Code.PORT_NOT_SET),
                    port == null ? "null" : port
            );
        }

        try{
            int portI = Integer.parseInt(port);
        } catch (NumberFormatException ex) {
            return DefaultResultBuilder.<MongoClientSettings>fail(CR.get(Code.INVALID_PORT), port);
        }

        if (databaseName == null || databaseName.isBlank()) {
            return DefaultResultBuilder.<MongoClientSettings>fail(
                    CR.get(Code.INVALID_DATABASE_NAME),
                    databaseName == null ? "null" : databaseName
            );
        }

        return null;
    }
}
