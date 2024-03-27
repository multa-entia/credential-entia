package ru.multa.entia.credential.impl.data.manager;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.multa.entia.credential.api.data.manager.command.ManagerCommand;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.utils.Results;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultManagerTest {

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckCreation_ifQueueNull() {
        DefaultManager manager = new DefaultManager(null, null);

        Field field = manager.getClass().getDeclaredField("queue");
        field.setAccessible(true);
        BlockingQueue<ManagerCommand> gottenQueue = (BlockingQueue<ManagerCommand>) field.get(manager);

        assertThat(gottenQueue.size() + gottenQueue.remainingCapacity()).isEqualTo(10_000);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckCreation_ifQueueNotNull() {
        ArrayBlockingQueue<ManagerCommand> expectedQueue = new ArrayBlockingQueue<>(10);

        DefaultManager manager = new DefaultManager(expectedQueue, null);

        Field field = manager.getClass().getDeclaredField("queue");
        field.setAccessible(true);
        BlockingQueue<ManagerCommand> gottenQueue = (BlockingQueue<ManagerCommand>) field.get(manager);

        assertThat(gottenQueue).isEqualTo(expectedQueue);
    }

    @SneakyThrows
    @Test
    void shouldCheckCreation_ifThreadParamsNull() {
        DefaultManager manager = new DefaultManager(null, null);

        Field field = manager.getClass().getDeclaredField("threadParams");
        field.setAccessible(true);
        DefaultManager.ThreadParams gottenThreadParams = (DefaultManager.ThreadParams) field.get(manager);

        assertThat(gottenThreadParams).isEqualTo(new DefaultManager.ThreadParams());
    }

    @SneakyThrows
    @Test
    void shouldCheckCreation_ifThreadParamsNotNull() {
        DefaultManager.ThreadParams expectedThreadParams = new DefaultManager.ThreadParams(Faker.str_().random());

        DefaultManager manager = new DefaultManager(null, expectedThreadParams);

        Field field = manager.getClass().getDeclaredField("threadParams");
        field.setAccessible(true);
        DefaultManager.ThreadParams gottenThreadParams = (DefaultManager.ThreadParams) field.get(manager);

        assertThat(gottenThreadParams).isEqualTo(expectedThreadParams);
    }

    @SneakyThrows
    @Test
    void shouldCheckCreation_forAlive() {
        DefaultManager manager = new DefaultManager(null, null);

        Field field = manager.getClass().getDeclaredField("alive");
        field.setAccessible(true);
        AtomicBoolean gottenAlive = (AtomicBoolean) field.get(manager);

        assertThat(gottenAlive.get()).isFalse();
    }

    @Test
    void shouldCheckStarting_ifAlreadyStarted() {
        DefaultManager manager = new DefaultManager(null, null);
        manager.start();
        Result<Object> result = manager.start();

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultManager.Code.ALREADY_STARTED))
                        .back()
                        .compare()
        ).isTrue();
    }

    @SneakyThrows
    @Test
    void shouldCheckStarting() {
        DefaultManager manager = new DefaultManager(null, null);
        Result<Object> result = manager.start();

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(null)
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Class<? extends DefaultManager> type = manager.getClass();
        Field field = type.getDeclaredField("alive");
        field.setAccessible(true);
        AtomicBoolean gottenAlive = (AtomicBoolean) field.get(manager);

        field = type.getDeclaredField("es");
        field.setAccessible(true);
        Object gottenES = field.get(manager);

        assertThat(gottenAlive.get()).isTrue();
        assertThat(gottenES).isNotNull();
    }

    @Test
    void shouldCheckStopping_ifAlreadyStarted() {
        DefaultManager manager = new DefaultManager(null, null);
        Result<Object> result = manager.stop();

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultManager.Code.ALREADY_STOPPED))
                        .back()
                        .compare()
        ).isTrue();
    }

    @SneakyThrows
    @Test
    void shouldCheckStopping() {
        DefaultManager manager = new DefaultManager(null, null);
        manager.start();
        Result<Object> result = manager.stop();

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(null)
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();

        Class<? extends DefaultManager> type = manager.getClass();
        Field field = type.getDeclaredField("alive");
        field.setAccessible(true);
        AtomicBoolean gottenAlive = (AtomicBoolean) field.get(manager);

        field = type.getDeclaredField("es");
        field.setAccessible(true);
        Object gottenES = field.get(manager);

        assertThat(gottenAlive.get()).isFalse();
        assertThat(gottenES).isNull();
    }

    @Test
    void shouldCheckOffer_ifNotStarted() {
        Supplier<ManagerCommand> supplier = () -> {
            return Mockito.mock(ManagerCommand.class);
        };

        DefaultManager manager = new DefaultManager(null, null);
        Result<Object> result = manager.offer(supplier.get());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultManager.Code.OFFER_IF_NOT_STARTED))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckOffer_ifFull() {
        Supplier<ManagerCommand> supplier = () -> {
            ManagerCommand command = Mockito.mock(ManagerCommand.class);
            Mockito
                    .doAnswer(new Answer<Void>() {
                        @Override
                        public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                            Thread.sleep(50);
                            return null;
                        }
                    })
                    .when(command)
                    .execute();

            return command;
        };

        DefaultManager manager = new DefaultManager(new ArrayBlockingQueue<>(1), null);
        manager.start();
        manager.offer(supplier.get());
        manager.offer(supplier.get());
        Result<Object> result = manager.offer(supplier.get());

        assertThat(
                Results.comparator(result)
                        .isFail()
                        .value(null)
                        .seedsComparator()
                        .code(CR.get(DefaultManager.Code.OFFER_QUEUE_IS_FULL))
                        .back()
                        .compare()
        ).isTrue();
    }

    @Test
    void shouldCheckOffer() {
        Supplier<ManagerCommand> supplier = () -> {return Mockito.mock(ManagerCommand.class);};

        DefaultManager manager = new DefaultManager(null, null);
        manager.start();
        Result<Object> result = manager.offer(supplier.get());

        assertThat(
                Results.comparator(result)
                        .isSuccess()
                        .value(null)
                        .seedsComparator()
                        .isNull()
                        .back()
                        .compare()
        ).isTrue();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution() {
        AtomicBoolean holder = new AtomicBoolean();
        ManagerCommand command = new ManagerCommand() {
            @Override
            public void execute() {
                holder.set(true);
            }
        };

        DefaultManager manager = new DefaultManager(null, null);
        manager.start();
        manager.offer(command);
        Thread.sleep(10);

        assertThat(holder.get()).isTrue();
    }
}