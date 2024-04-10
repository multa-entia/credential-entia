package ru.multa.entia.credential.impl.data.manager;

import lombok.extern.slf4j.Slf4j;
import ru.multa.entia.credential.api.data.manager.Manager;
import ru.multa.entia.credential.api.data.manager.command.ManagerCommand;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Slf4j
public class DefaultManager implements Manager {
    public enum Code {
        ALREADY_STARTED,
        ALREADY_STOPPED,
        OFFER_IF_NOT_STARTED,
        OFFER_QUEUE_IS_FULL
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.ALREADY_STARTED, "credential:manager.default:already-started");
        CR.update(Code.ALREADY_STOPPED, "credential:manager.default:already-stopped");
        CR.update(Code.OFFER_IF_NOT_STARTED, "credential:manager.default:offer-if-not-started");
        CR.update(Code.OFFER_QUEUE_IS_FULL, "credential:manager.default:offer-queue-is-full");
    }

    private static final AtomicInteger THREAD_NAME_COUNTER = new AtomicInteger(0);
    private static final int DEFAULT_QUEUE_SIZE = 10_000;
    private static final Function<ThreadParams, ExecutorService> ES_FUNCTION = params -> Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName(params.prefix() + THREAD_NAME_COUNTER.incrementAndGet());

        return thread;
    });

    private final BlockingQueue<ManagerCommand> queue;
    private final ThreadParams threadParams;
    private final AtomicBoolean alive = new AtomicBoolean(false);

    private ExecutorService es;

    public DefaultManager(final BlockingQueue<ManagerCommand> queue, final ThreadParams threadParams) {
        this.queue = Objects.requireNonNullElse(queue, new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE));
        this.threadParams = Objects.requireNonNullElse(threadParams, new ThreadParams());
    }

    @Override
    public Result<Object> start() {
        log.info("The attempt of starting");
        String code = CR.get(Code.ALREADY_STARTED);
        if (alive.compareAndSet(false, true)) {
            log.info("Started");
            es = ES_FUNCTION.apply(threadParams);
            es.submit(this::processES);
            code = null;
        }

        return DefaultResultBuilder.<Object>compute(null, code);
    }

    @Override
    public Result<Object> stop() {
        log.info("The attempt of stopping");
        String code = CR.get(Code.ALREADY_STOPPED);
        if (alive.compareAndSet(true, false)) {
            log.info("Stopped");
            es.shutdown();
            es = null;
            code = null;
        }

        return DefaultResultBuilder.<Object>compute(null, code);
    }

    @Override
    public Result<Object> offer(final ManagerCommand command) {
        log.info("The attempt of offer: {}", command);
        return DefaultResultBuilder.<Object>computeFromCodes(
                () -> {return null;},
                () -> {
                    Code code = Code.OFFER_IF_NOT_STARTED;
                    if (alive.get()) {
                        code = queue.offer(command) ? null : Code.OFFER_QUEUE_IS_FULL;
                    }

                    return code != null ? CR.get(code) : null;
                }
        );
    }

    private void processES(){
        log.info("DefaultManager processing is started");
        while (alive.get()) {
            try {
                ManagerCommand command = queue.take();
                command.execute();
            } catch (InterruptedException exception) {
                log.error(exception.getMessage(), exception);
                Thread.currentThread().interrupt();
            }
        }
        log.info("DefaultManager processing is finished");
    }

    public record ThreadParams(String prefix) {
        private static final String DEFAULT_PREFIX = "box-processor-thread";

        public ThreadParams() {
            this(DEFAULT_PREFIX);
        }

        public ThreadParams(final String prefix) {
            this.prefix = prefix != null && !prefix.isBlank()
                    ? prefix
                    : String.format("%s-%s-", DEFAULT_PREFIX, UUID.randomUUID());
        }
    }
}
