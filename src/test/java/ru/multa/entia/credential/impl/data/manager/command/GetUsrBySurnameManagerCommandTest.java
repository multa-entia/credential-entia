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

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class GetUsrBySurnameManagerCommandTest {

    @Test
    void shouldCheckFindingByPaterName_ifFail() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.seed().code());
        };

        String expectedCode = Faker.str_().random();
        Supplier<UsrService> usrServiceSupplier = () -> {
            Result<List<Usr>> result = DefaultResultBuilder.<List<Usr>>fail(expectedCode);
            UsrService service = Mockito.mock(UsrService.class);
            Mockito
                    .when(service.getBySurname(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        new GetUsrBySurnameManagerCommand(consumer, usrServiceSupplier.get(), Faker.str_().random()).execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(expectedCode);
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldCheckFindingByPaterName() {
        DefaultUsr expectedUsr = new DefaultUsr(
                new ObjectId(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random()
        );

        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<List<Usr>> usrHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            usrHolder.set((List<Usr>) result.value().get(GetUsrByFirstNameManagerCommand.PROPERTY_USERS).value());
        };

        Supplier<UsrService> usrServiceSupplier = () -> {
            Result<List<Usr>> result = DefaultResultBuilder.<List<Usr>>ok(List.of(expectedUsr));
            UsrService service = Mockito.mock(UsrService.class);
            Mockito
                    .when(service.getBySurname(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        new GetUsrBySurnameManagerCommand(consumer, usrServiceSupplier.get(), Faker.str_().random()).execute();

        assertThat(okHolder.get()).isTrue();
        assertThat(usrHolder.get()).hasSize(1);
        assertThat(usrHolder.get().get(0)).isEqualTo(expectedUsr);
    }
}