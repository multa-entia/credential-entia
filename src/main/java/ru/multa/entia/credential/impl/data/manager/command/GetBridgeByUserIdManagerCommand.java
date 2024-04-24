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

import java.util.List;
import java.util.function.Consumer;

public class GetBridgeByUserIdManagerCommand extends AbstractManagerCommand{
    public static final String PROPERTY_BRIDGES = "bridges";

    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.get-bridge-by-user-id:service-is-absence");
    }

    private final ObjectId userId;

    @Setter
    private BridgeService bridgeService;

    public GetBridgeByUserIdManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                           final ObjectId userId) {
        super(consumer);
        this.userId = userId;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (bridgeService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<List<Bridge>> result = bridgeService.getByUserId(userId);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_BRIDGES, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
