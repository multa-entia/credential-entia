package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

// TODO: test
@Configuration()
@ConfigurationProperties(prefix = "mongo")
@Setter
public class MongoConfig {
    private PropertiesSetType type;
    private LocalParams local;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString cs = new ConnectionString("mongodb://localhost:27017/test");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(cs)
                .build();
        // TODO: !!!
//        System.out.println(cs);
//        throw new Exception("123");

        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        // TODO: !!!
        System.out.println("++++++++++++++++++++++");
        return new MongoTemplate(mongoClient(), "test");
    }

    // TODO: test
    @Setter
    @Getter
    public static class LocalParams {
        private String scheme;
        private String host;
        private int port;
        private String databaseName;

        public MongoClientSettings getSettings() {
            return null;
        }
    }
}
