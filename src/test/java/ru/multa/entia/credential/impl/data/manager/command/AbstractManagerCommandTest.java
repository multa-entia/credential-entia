package ru.multa.entia.credential.impl.data.manager.command;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;
import ru.multa.entia.results.utils.Results;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractManagerCommandTest {

    @Test
    void shouldCheckExecution() {
        String expectedValue = Faker.str_().random();
        Result<Object> expectedResult = DefaultResultBuilder.<Object>ok(expectedValue);

        Supplier<ManagerDatum> supplier = () -> {
            ManagerDatum item = Mockito.mock(ManagerDatum.class);
            Mockito
                    .when(item.get(Mockito.any(), Mockito.any()))
                    .thenReturn(expectedResult);

            return item;
        };

        AtomicReference<Result<ManagerDatum>> holder = new AtomicReference<>();
        Consumer<Result<ManagerDatum>> consumer = new Consumer<Result<ManagerDatum>>() {
            @Override
            public void accept(Result<ManagerDatum> result) {
                holder.set(result);
            }
        };

        AbstractManagerCommand command = new AbstractManagerCommand(consumer) {
            @Override
            protected Result<ManagerDatum> executeService() {
                return DefaultResultBuilder.<ManagerDatum>ok(supplier.get());
            }
        };
        command.execute();

        ManagerDatum managerDatum = holder.get().value();
        Result<String> result = managerDatum.get("", String.class);

        assertThat(Results.comparator(result)
                .isSuccess()
                .value(expectedValue)
                .seedsComparator()
                .isNull()
                .back()
                .compare()
        ).isTrue();
    }
}