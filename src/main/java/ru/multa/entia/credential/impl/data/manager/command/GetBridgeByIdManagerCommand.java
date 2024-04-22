package ru.multa.entia.credential.impl.data.manager.command;

import lombok.Setter;
import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.api.data.bridge.BridgeService;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.impl.data.manager.item.DefaultManagerDatum;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.function.Consumer;

public class GetBridgeByIdManagerCommand extends AbstractManagerCommand{
    public static final String PROPERTY_BRIDGE = "bridge";

    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.get-bridge-by-id:service-is-absence");
    }

    private final ObjectId id;

    @Setter
    private BridgeService bridgeService;

    public GetBridgeByIdManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                       final ObjectId id) {
        super(consumer);
        this.id = id;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (bridgeService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<Bridge> result = bridgeService.getById(id);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_BRIDGE, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
