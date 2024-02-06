package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.client.MongoClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.multa.entia.credential.api.config.mongo.MongoProperties;

import java.util.List;

@Configuration()
//@ConfigurationProperties(prefix = "mongo")
//@Setter
public class MongoConfig {
//    private MongoPropertiesAdapter adapter;
//    private PropertiesSetType type;
//    private LocalMongoPropertiesOld local;
    // TODO: del
//    private String databaseName;


    @Autowired
    private List<MongoProperties> properties;

    @Bean
    public MongoClient mongoClient() {
        throw new RuntimeException("11");
        // TODO: !!!
//        adapter = createAdapter();
//        Result<MongoClientSettings> result = adapter.getSettings();
//        if (!result.ok()){
//            throw new RuntimeException(result.seed().code());
//        }
//
//        return MongoClients.create(result.value());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        throw new RuntimeException("!");
        // TODO: !!!
//        return new MongoTemplate(mongoClient(), adapter.getDatabaseName());
    }

    //<
//    private MongoPropertiesAdapter createAdapter() {
//        if (adapter == null) {
//            adapter = new MongoPropertiesAdapter();
//            adapter.setType(type);
//            if (local != null) {
//                adapter.register(LocalMongoProperties.TYPE, local);
//            }
//        }
//        return adapter;
//    }
}
