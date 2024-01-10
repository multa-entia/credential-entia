package ru.multa.entia.credential.api.config.mongo;

import com.mongodb.MongoClientSettings;
import ru.multa.entia.results.api.result.Result;


public interface MongoProperties {
    Result<MongoClientSettings> getSettings();
}
