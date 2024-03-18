package ru.multa.entia.credential.impl.data.manager;

import lombok.extern.slf4j.Slf4j;
import ru.multa.entia.credential.api.data.manager.Manager;
import ru.multa.entia.credential.api.data.manager.strategy.ManagerStrategy;
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

    private final BlockingQueue<ManagerStrategy> queue;
    private final ThreadParams threadParams;
    private final AtomicBoolean alive = new AtomicBoolean(false);

    private ExecutorService es;

    public DefaultManager(final BlockingQueue<ManagerStrategy> queue, final ThreadParams threadParams) {
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
    public Result<Object> offer(ManagerStrategy strategy) {
        // TODO: impl
        return null;
    }

    private void processES(){
//        log.info("Box processing started");
//
//        while (alive.get()) {
//            try {
//                PipelineBox<TASK> box = queue.take();
//                receiver.receive(sessionId, box);
//            } catch (InterruptedException exception) {
//                log.error(exception.getMessage(), exception);
//                Thread.currentThread().interrupt();
//            }
//        }
//        log.info("Box processing finished");
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

// TODO: !!!
//abstract public class AbstractPipeline<T extends ConversationItem, TASK> implements Pipeline<TASK> {
//    public enum Code {
//        ALREADY_STARTED,
//        ALREADY_STOPPED,
//        OFFER_IF_NOT_STARTED,
//        OFFER_QUEUE_IS_FULL
//    }
//
//    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
//    private static final AtomicInteger threadNameCounter = new AtomicInteger(0);
//
//    private static final Function<ThreadParams, ExecutorService> BOX_PROCESSOR_FUNCTION = params -> Executors.newSingleThreadExecutor(r -> {
//        Thread thread = new Thread(r);
//        thread.setName(params.prefix() + threadNameCounter.incrementAndGet());
//
//        return thread;
//    });
//
//    private final AtomicBoolean alive = new AtomicBoolean(false);
//    private final BlockingQueue<PipelineBox<TASK>> queue;
//    private final PipelineReceiver<TASK> receiver;
//    private final ThreadParams threadParams;
//
//    private UUID sessionId;
//    private ExecutorService boxProcessor;
//
//    public AbstractPipeline(final BlockingQueue<PipelineBox<TASK>> queue,
//                            final PipelineReceiver<TASK> receiver) {
//        this(queue, receiver, null);
//    }
//
//    public AbstractPipeline(final BlockingQueue<PipelineBox<TASK>> queue,
//                            final PipelineReceiver<TASK> receiver,
//                            final ThreadParams threadParams) {
//        this.queue = queue;
//        this.receiver = receiver;
//        this.threadParams = Objects.requireNonNullElse(threadParams, new ThreadParams());
//    }
//
//    @Override
//    public Result<Object> start() {

//    }
//
//    @Override
//    public Result<Object> stop(boolean clear) {
//        String code = CR.get(new CodeKey(getClass(), Code.ALREADY_STOPPED));
//        log.info("The attempt of stopping");
//        if (alive.compareAndSet(true, false)){
//            log.info("Stopped");
//            sessionId = null;
//            receiver.block();
//            boxProcessor.shutdown();
//            boxProcessor = null;
//            if (clear){
//                queue.clear();
//            }
//            code = null;
//        }
//
//        return DefaultResultBuilder.<Object>compute(null, code);
//    }
//
//    @Override
//    public Result<TASK> offer(final PipelineBox<TASK> box) {
//        log.info("The attempt of offer: {}", box.value());
//
//        return DefaultResultBuilder.<TASK>computeFromCodes(
//                box::value,
//                () -> {
//                    Code code = Code.OFFER_IF_NOT_STARTED;
//                    if (alive.get()){
//                        code = queue.offer(box) ? null : Code.OFFER_QUEUE_IS_FULL;
//                    }
//
//                    return code != null ? CR.get(new CodeKey(getClass(), code)) : null;
//                }
//        );
//    }
//

//

//
//    public record CodeKey(Class<?> type, Object key) {}
