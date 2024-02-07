package ru.multa.entia.credential.impl.config.decryptor;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.multa.entia.parameters.api.decryptor.Decryptor;
import ru.multa.entia.parameters.impl.decryptor.DefaultStringDecryptor;
import ru.multa.entia.parameters.impl.getter.DefaultEnvVarGetter;
import ru.multa.entia.results.api.result.Result;

@Configuration
@Setter
public class DecryptorConfig {

    @Value("${jasypt.password.name}")
    private String passwordName;

    @Bean
    public Decryptor<String, Result<String>> decryptor() {
        Result<String> passwordResult = new DefaultEnvVarGetter().get(passwordName);
        if (!passwordResult.ok()) {
            throw new RuntimeException(passwordResult.seed().code());
        }

        Result<DefaultStringDecryptor> result = DefaultStringDecryptor.create(passwordResult.value());
        if (!result.ok()) {
            throw new RuntimeException(result.seed().code());
        }

        return result.value();
    }
}
