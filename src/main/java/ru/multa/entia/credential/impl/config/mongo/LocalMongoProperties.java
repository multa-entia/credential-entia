package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import lombok.Getter;
import lombok.Setter;
import ru.multa.entia.credential.api.config.mongo.MongoProperties;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;
import ru.multa.entia.results.impl.seed.DefaultSeedBuilder;

import java.util.concurrent.atomic.AtomicReference;

@Setter
@Getter
class LocalMongoProperties implements MongoProperties {
    public enum  Code {
        INVALID_SCHEME,
        INVALID_HOST,
        PORT_NOT_SET,
        INVALID_PORT,
        INVALID_DATABASE_NAME,
        INVALID_CONNECTION_STRING
    }

    private static final String CONNECTION_STRING_TEMPLATE = "%s://%s:%s/%s";
    public static final PropertiesSetType TYPE = PropertiesSetType.LOCAL;
    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.INVALID_SCHEME, "credential:properties.mongo.local:invalid-scheme");
        CR.update(Code.INVALID_HOST, "credential:properties.mongo.local:invalid-host");
        CR.update(Code.PORT_NOT_SET, "credential:properties.mongo.local:port-not-set");
        CR.update(Code.INVALID_PORT, "credential:properties.mongo.local:invalid-port");
        CR.update(Code.INVALID_DATABASE_NAME, "credential:properties.mongo.local:invalid-database-name");
        CR.update(Code.INVALID_CONNECTION_STRING, "credential:properties.mongo.local:invalid-connection-string");
    }

    private static Seed checkOnEmptiness(final String value, final Code code) {
        return value == null || value.isBlank()
                ? new DefaultSeedBuilder<MongoClientSettings>()
                    .code(CR.get(code))
                    .addLastArgs(value == null ? "null" : value)
                    .build()
                : null;
    }

    private String scheme;
    private String host;
    private String port;
    private String databaseName;

    @Override
    public Result<MongoClientSettings> getSettings() {
        AtomicReference<MongoClientSettings> reference = new AtomicReference<>();
        return DefaultResultBuilder.<MongoClientSettings>compute(
                reference::get,
                () -> {
                    return checkOnEmptiness(scheme, Code.INVALID_SCHEME);
                },
                () -> {
                    return checkOnEmptiness(host, Code.INVALID_HOST);
                },
                () -> {
                    return checkOnEmptiness(port, Code.PORT_NOT_SET);
                },
                () -> {
                    Code code = null;
                    try{
                        int portI = Integer.parseInt(port);
                        if (portI < 0 || portI > 0xFFFF) {
                            code = Code.INVALID_PORT;
                        }
                    } catch (NumberFormatException ex) {
                        code = Code.INVALID_PORT;
                    }
                    return code != null
                            ? new DefaultSeedBuilder<MongoClientSettings>().code(CR.get(Code.INVALID_PORT)).addLastArgs(port).build()
                            : null;
                },
                () -> {
                    return checkOnEmptiness(databaseName, Code.INVALID_DATABASE_NAME);
                },
                () -> {
                    try{
                        ConnectionString cs = new ConnectionString(String.format(CONNECTION_STRING_TEMPLATE, scheme, host, port, databaseName));
                        MongoClientSettings settings = MongoClientSettings.builder()
                                .applyConnectionString(cs)
                                .build();
                        reference.set(settings);
                        return null;
                    } catch (RuntimeException ex) {
                        return new DefaultSeedBuilder<MongoClientSettings>()
                                .code(CR.get(Code.INVALID_CONNECTION_STRING))
                                .addLastArgs(ex.getMessage())
                                .build();
                    }
                }
        );
    }
}
