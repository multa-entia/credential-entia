package ru.multa.entia.credential.impl.data.manager.command;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.credential.impl.data.usr.DefaultUsr;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class SaveUsrManagerCommandTest {

    @Test
    void shouldCheckFailSaving() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.seed().code());
        };

        String expectedCode = Faker.str_().random();
        Supplier<UsrService> usrServiceSupplier = () -> {
            Result<Usr> result = DefaultResultBuilder.<Usr>fail(expectedCode);
            UsrService service = Mockito.mock(UsrService.class);
            Mockito
                    .when(service.save(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        DefaultUsr usr = new DefaultUsr(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );
        new SaveUsrManagerCommand(consumer, usrServiceSupplier.get(), usr).execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(expectedCode);
    }

    @Test
    void shouldCheckSaving() {
        DefaultUsr expectedUsr = new DefaultUsr(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<Usr> usrHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            usrHolder.set(result.value().get(SaveUsrManagerCommand.PROPERTY_USR, DefaultUsr.class).value());
        };

        Supplier<UsrService> usrServiceSupplier = () -> {
            Result<Usr> result = DefaultResultBuilder.<Usr>ok(expectedUsr);
            UsrService service = Mockito.mock(UsrService.class);
            Mockito
                    .when(service.save(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        new SaveUsrManagerCommand(consumer, usrServiceSupplier.get(), expectedUsr).execute();

        assertThat(okHolder.get()).isTrue();
        assertThat(usrHolder.get()).isEqualTo(expectedUsr);
    }
}