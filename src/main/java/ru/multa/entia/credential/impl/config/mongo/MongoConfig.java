package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.multa.entia.results.api.result.Result;

@Configuration()
@ConfigurationProperties(prefix = "mongo")
@Setter
public class MongoConfig {
    private MongoPropertiesAdapter adapter;
    private PropertiesSetType type;
    private LocalMongoProperties local;
    private String databaseName;

    @Bean
    public MongoClient mongoClient() {
        adapter = createAdapter();
        Result<MongoClientSettings> result = adapter.getSettings();
        if (!result.ok()){
            throw new RuntimeException(result.seed().code());
        }

        return MongoClients.create(result.value());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), adapter.getDatabaseName());
    }

    private MongoPropertiesAdapter createAdapter() {
        if (adapter == null) {
            adapter = new MongoPropertiesAdapter();
            adapter.setType(type);
            if (local != null) {
                adapter.register(LocalMongoProperties.TYPE, local);
            }
        }
        return adapter;
    }
}
