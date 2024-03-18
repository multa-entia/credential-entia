package ru.multa.entia.credential.impl.data.manager;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.multa.entia.credential.api.data.manager.strategy.ManagerStrategy;
import ru.multa.entia.fakers.impl.Faker;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.utils.Results;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

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
        BlockingQueue<ManagerStrategy> gottenQueue = (BlockingQueue<ManagerStrategy>) field.get(manager);

        assertThat(gottenQueue.size() + gottenQueue.remainingCapacity()).isEqualTo(10_000);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Test
    void shouldCheckCreation_ifQueueNotNull() {
        ArrayBlockingQueue<ManagerStrategy> expectedQueue = new ArrayBlockingQueue<>(10);

        DefaultManager manager = new DefaultManager(expectedQueue, null);

        Field field = manager.getClass().getDeclaredField("queue");
        field.setAccessible(true);
        BlockingQueue<ManagerStrategy> gottenQueue = (BlockingQueue<ManagerStrategy>) field.get(manager);

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
}