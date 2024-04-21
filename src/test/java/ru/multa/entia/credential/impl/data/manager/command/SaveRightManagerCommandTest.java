package ru.multa.entia.credential.impl.data.manager.command;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightService;
import ru.multa.entia.credential.impl.data.right.DefaultRight;
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

class SaveRightManagerCommandTest {

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();

    @Test
    void shouldCheckCommandExecution_ifServiceIsAbsence() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.seed().code());
        };

        new SaveRightManagerCommand(consumer, new DefaultRight(new ObjectId(), Faker.str_().random())).execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(CR.get(SaveRightManagerCommand.Code.SERVICE_IS_ABSENCE));
    }

    @Test
    void shouldCheckCommandExecution_ifEntityIsAbsence() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.seed().code());
        };

        String expectedCode = Faker.str_().random();
        Supplier<RightService> rightServiceSupplier = () -> {
            Result<Right> result = DefaultResultBuilder.<Right>fail(expectedCode);
            RightService service = Mockito.mock(RightService.class);
            Mockito
                    .when(service.save(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        SaveRightManagerCommand command = new SaveRightManagerCommand(consumer, new DefaultRight(new ObjectId(), Faker.str_().random()));
        command.setRightService(rightServiceSupplier.get());
        command.execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(expectedCode);
    }

    @Test
    void shouldCheckCommandExecution() {
        DefaultRight expectedRight = new DefaultRight(new ObjectId(), Faker.str_().random());

        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<Right> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.value().get(GetRightByIdMangerCommand.PROPERTY_RIGHT, DefaultRight.class).value());
        };

        Supplier<RightService> rightServiceSupplier = () -> {
            Result<Right> result = DefaultResultBuilder.<Right>ok(expectedRight);
            RightService service = Mockito.mock(RightService.class);
            Mockito
                    .when(service.save(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        SaveRightManagerCommand command = new SaveRightManagerCommand(consumer, new DefaultRight(new ObjectId(), Faker.str_().random()));
        command.setRightService(rightServiceSupplier.get());
        command.execute();

        assertThat(okHolder.get()).isTrue();
        assertThat(codeHolder.get()).isEqualTo(expectedRight);
    }
}