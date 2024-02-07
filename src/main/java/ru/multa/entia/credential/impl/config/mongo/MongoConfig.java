package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.config.mongo.MongoProperties;
import ru.multa.entia.parameters.api.decryptor.Decryptor;
import ru.multa.entia.results.api.result.Result;

import java.util.List;

@Configuration()
@Setter
public class MongoConfig {

    @Value("${mongo.collector.type}")
    private String type;

    @Autowired
    private List<MongoProperties> properties;

    @Autowired
    private Decryptor<String, Result<String>> decryptor;

    private MongoProperties property;

    @Bean
    public MongoClient mongoClient() {
        for (MongoProperties prop : properties) {
            String value = prop.getClass().getAnnotation(Component.class).value();
            if (type.equals(value)) {
                property = prop;
                break;
            }
        }

        if (property == null) {
            throw new RuntimeException("No one mongo properties");
        }

        return MongoClients.create(property.getSettings(decryptor).value());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), property.getDatabaseName(decryptor));
    }
}
