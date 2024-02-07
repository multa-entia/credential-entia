package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.config.mongo.MongoProperties;
import ru.multa.entia.parameters.api.decryptor.Decryptor;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.api.seed.Seed;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;
import ru.multa.entia.results.impl.seed.DefaultSeedBuilder;

import java.util.concurrent.atomic.AtomicReference;

@Component("localMongoProperties")
public class LocalMongoProperties implements MongoProperties {
    public enum  Code {
        INVALID_SCHEME,
        INVALID_HOST,
        PORT_NOT_SET,
        INVALID_PORT,
        INVALID_DATABASE_NAME,
        INVALID_CONNECTION_STRING
    }

    private static final String CONNECTION_STRING_TEMPLATE = "%s://%s:%s/%s";
    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(LocalMongoProperties.Code.INVALID_SCHEME, "credential:properties.mongo.local:invalid-scheme");
        CR.update(LocalMongoProperties.Code.INVALID_HOST, "credential:properties.mongo.local:invalid-host");
        CR.update(LocalMongoProperties.Code.PORT_NOT_SET, "credential:properties.mongo.local:port-not-set");
        CR.update(LocalMongoProperties.Code.INVALID_PORT, "credential:properties.mongo.local:invalid-port");
        CR.update(LocalMongoProperties.Code.INVALID_DATABASE_NAME, "credential:properties.mongo.local:invalid-database-name");
        CR.update(LocalMongoProperties.Code.INVALID_CONNECTION_STRING, "credential:properties.mongo.local:invalid-connection-string");
    }
    private static Seed checkOnEmptiness(final String value, final Code code) {
        return value == null || value.isBlank()
                ? new DefaultSeedBuilder<MongoClientSettings>()
                    .code(CR.get(code))
                    .addLastArgs(value == null ? "null" : value)
                    .build()
                : null;
    }

    private final String scheme;
    private final String host;
    private final String port;
    private final String databaseName;

    public LocalMongoProperties(@Value("${mongo.properties.localMongoProperties.scheme:}") String scheme,
                                @Value("${mongo.properties.localMongoProperties.host:}") String host,
                                @Value("${mongo.properties.localMongoProperties.port:}") String port,
                                @Value("${mongo.properties.localMongoProperties.databaseName:}") String databaseName) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
    }

    @Override
    public Result<MongoClientSettings> getSettings(final Decryptor<String, Result<String>> decryptor) {
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
                        int portI = Integer.parseInt(decryptor.decrypt(port).value());
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
                        ConnectionString cs = new ConnectionString(String.format(
                                CONNECTION_STRING_TEMPLATE,
                                decryptor.decrypt(scheme).value(),
                                decryptor.decrypt(host).value(),
                                decryptor.decrypt(port).value(),
                                decryptor.decrypt(databaseName).value()
                        ));
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

    @Override
    public String getDatabaseName(final Decryptor<String, Result<String>> decryptor) {
        return decryptor.decrypt(databaseName).value();
    }
}
