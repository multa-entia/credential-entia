package ru.multa.entia.credential.impl.converter.usr;

import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrEntity;
import ru.multa.entia.credential.impl.data.usr.UsrImpl;

import java.util.function.Function;

@Component
public class DefaultEntity2UsrConverter implements Function<UsrEntity, Usr> {
    @Override
    public Usr apply(final UsrEntity usrEntity) {
        return new UsrImpl(
                usrEntity.getId(),
                usrEntity.getFirstName(),
                usrEntity.getPaterName(),
                usrEntity.getSurname(),
                usrEntity.getEmail()
        );
    }
}
