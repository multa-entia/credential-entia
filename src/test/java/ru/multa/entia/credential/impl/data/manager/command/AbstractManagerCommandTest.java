package ru.multa.entia.credential.impl.data.manager.command;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.multa.entia.credential.api.data.manager.item.ManagerItem;
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

        Supplier<ManagerItem> supplier = () -> {
            ManagerItem item = Mockito.mock(ManagerItem.class);
            Mockito
                    .when(item.get(Mockito.any(), Mockito.any()))
                    .thenReturn(expectedResult);

            return item;
        };

        AtomicReference<ManagerItem> holder = new AtomicReference<>();
        Consumer<ManagerItem> consumer = new Consumer<ManagerItem>() {
            @Override
            public void accept(ManagerItem item) {
                holder.set(item);
            }
        };

        AbstractManagerCommand command = new AbstractManagerCommand(consumer) {
            @Override
            protected ManagerItem executeService() {
                return supplier.get();
            }
        };
        command.execute();

        ManagerItem managerItem = holder.get();
        Result<String> result = managerItem.get("", String.class);

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