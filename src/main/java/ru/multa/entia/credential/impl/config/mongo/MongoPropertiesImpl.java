package ru.multa.entia.credential.impl.config.mongo;

import com.mongodb.MongoClientSettings;
import ru.multa.entia.credential.api.config.mongo.MongoProperties;
import ru.multa.entia.results.api.result.Result;

// TODO: impl + test
class MongoPropertiesImpl implements MongoProperties {

    @Override
    public Result<MongoClientSettings> getSettings() {
        return null;
    }
}
