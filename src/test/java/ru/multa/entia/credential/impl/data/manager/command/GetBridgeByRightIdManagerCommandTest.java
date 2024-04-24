package ru.multa.entia.credential.impl.data.manager.command;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.api.data.bridge.BridgeService;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.impl.data.bridge.DefaultBridge;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class GetBridgeByRightIdManagerCommandTest {
    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();

    @Test
    void shouldCheckCommandExecution_ifServiceIsAbsence() {
        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.seed().code());
        };

        new GetBridgeByRightIdManagerCommand(consumer, new ObjectId()).execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(CR.get(GetBridgeByRightIdManagerCommand.Code.SERVICE_IS_ABSENCE));
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
        Supplier<BridgeService> bridgeServiceSupplier = () -> {
            Result<List<Bridge>> result = DefaultResultBuilder.<List<Bridge>>fail(expectedCode);
            BridgeService service = Mockito.mock(BridgeService.class);
            Mockito
                    .when(service.getByRightId(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        GetBridgeByRightIdManagerCommand command = new GetBridgeByRightIdManagerCommand(consumer, new ObjectId());
        command.setBridgeService(bridgeServiceSupplier.get());
        command.execute();

        assertThat(okHolder.get()).isFalse();
        assertThat(codeHolder.get()).isEqualTo(expectedCode);
    }

    @Test
    void shouldCheckCommandExecution() {
        DefaultBridge expectedBridge = new DefaultBridge(new ObjectId(), new ObjectId(), new ObjectId());

        AtomicBoolean okHolder = new AtomicBoolean(false);
        AtomicReference<Object> codeHolder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = result -> {
            okHolder.set(result.ok());
            codeHolder.set(result.value().get(GetBridgeByRightIdManagerCommand.PROPERTY_BRIDGES).value());
        };

        Supplier<BridgeService> bridgeServiceSupplier = () -> {
            Result<List<Bridge>> result = DefaultResultBuilder.<List<Bridge>>ok(List.of(expectedBridge));
            BridgeService service = Mockito.mock(BridgeService.class);
            Mockito
                    .when(service.getByRightId(Mockito.any()))
                    .thenReturn(result);

            return service;
        };

        GetBridgeByRightIdManagerCommand command = new GetBridgeByRightIdManagerCommand(consumer, new ObjectId());
        command.setBridgeService(bridgeServiceSupplier.get());
        command.execute();

        assertThat(okHolder.get()).isTrue();
        assertThat(codeHolder.get()).isEqualTo(List.of(expectedBridge));
    }
}