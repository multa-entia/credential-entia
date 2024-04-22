package ru.multa.entia.credential.impl.data.manager.command;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightService;
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

class DeleteRightByValueManagerCommandTest {
    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();

    @Test
    void shouldCheckCommandExecution_ifServiceIsAbsence() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.seed().code());
        };

        new DeleteRightByValueManagerCommand(consumer, Faker.str_().random()).execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(CR.get(DeleteRightByValueManagerCommand.Code.SERVICE_IS_ABSENCE));
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
        Supplier<RightService> usrServiceSupplier = () -> {
            Result<Right> result = DefaultResultBuilder.<Right>fail(expectedCode);
            RightService service = Mockito.mock(RightService.class);
            Mockito
                    .when(service.deleteByValue(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        DeleteRightByValueManagerCommand command = new DeleteRightByValueManagerCommand(consumer, Faker.str_().random());
        command.setRightService(usrServiceSupplier.get());
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

        Supplier<RightService> usrServiceSupplier = () -> {
            Result<Right> result = DefaultResultBuilder.<Right>ok();
            RightService service = Mockito.mock(RightService.class);
            Mockito
                    .when(service.deleteByValue(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        DeleteRightByValueManagerCommand command = new DeleteRightByValueManagerCommand(consumer, Faker.str_().random());
        command.setRightService(usrServiceSupplier.get());
        command.execute();

        assertThat(okHolder.get()).isTrue();
    }
}