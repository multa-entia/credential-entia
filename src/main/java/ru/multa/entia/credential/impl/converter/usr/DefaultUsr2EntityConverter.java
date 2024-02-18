package ru.multa.entia.credential.impl.converter.usr;

import org.springframework.stereotype.Component;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrEntity;
import ru.multa.entia.credential.impl.data.usr.UsrEntityImpl;

import java.util.function.Function;

@Component
public class DefaultUsr2EntityConverter implements Function<Usr, UsrEntity> {
    @Override
    public UsrEntity apply(final Usr usr) {
        return new UsrEntityImpl(usr.id(), usr.firstName(), usr.paterName(), usr.surname(), usr.email());
    }
}
