package ru.multa.entia.credential.impl.data.manager.command;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteUsrByIdManagerCommandTest {

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();

    @Test
    void shouldCheckCommandExecution_ifServiceIsAbsence() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.seed().code());
        };

        new DeleteUsrByIdManagerCommand(consumer, new ObjectId()).execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(CR.get(DeleteUsrByIdManagerCommand.Code.SERVICE_IS_ABSENCE));
    }

    @Test
    void shouldCheckDeleting_ifFail() {
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
                    .when(service.deleteById(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        DeleteUsrByIdManagerCommand command = new DeleteUsrByIdManagerCommand(consumer, new ObjectId());
        command.setUsrService(usrServiceSupplier.get());
        command.execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(expectedCode);
    }

    @Test
    void shouldCheckDeleting() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
        };

        Supplier<UsrService> usrServiceSupplier = () -> {
            Result<Usr> result = DefaultResultBuilder.<Usr>ok();
            UsrService service = Mockito.mock(UsrService.class);
            Mockito
                    .when(service.deleteById(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        DeleteUsrByIdManagerCommand command = new DeleteUsrByIdManagerCommand(consumer, new ObjectId());
        command.setUsrService(usrServiceSupplier.get());
        command.execute();

        assertThat(okHolder.get()).isTrue();
    }
}