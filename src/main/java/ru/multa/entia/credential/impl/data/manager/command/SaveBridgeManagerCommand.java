package ru.multa.entia.credential.impl.data.manager.command;

import lombok.Setter;
import ru.multa.entia.credential.api.data.bridge.Bridge;
import ru.multa.entia.credential.api.data.bridge.BridgeService;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.impl.data.manager.item.DefaultManagerDatum;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.function.Consumer;

public class SaveBridgeManagerCommand extends AbstractManagerCommand {
    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.save-right:service-is-absence");
    }

    public static final String PROPERTY_BRIDGE = "bridge";

    private final Bridge bridge;

    @Setter
    private BridgeService bridgeService;

    public SaveBridgeManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                    final Bridge bridge) {
        super(consumer);
        this.bridge = bridge;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (bridgeService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<Bridge> result = bridgeService.save(bridge);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_BRIDGE, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
