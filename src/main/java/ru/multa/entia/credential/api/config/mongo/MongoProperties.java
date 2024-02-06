package ru.multa.entia.credential.api.config.mongo;

import com.mongodb.MongoClientSettings;
import ru.multa.entia.parameters.api.decryptor.Decryptor;
import ru.multa.entia.results.api.result.Result;


public interface MongoProperties {
    Result<MongoClientSettings> getSettings(Decryptor<String, Result<String>> decryptor);
    String getDatabaseName(Decryptor<String, Result<String>> decryptor);
}
