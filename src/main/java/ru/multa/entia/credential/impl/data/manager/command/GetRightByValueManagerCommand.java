package ru.multa.entia.credential.impl.data.manager.command;

import lombok.Setter;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.right.Right;
import ru.multa.entia.credential.api.data.right.RightService;
import ru.multa.entia.credential.impl.data.manager.item.DefaultManagerDatum;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.function.Consumer;

public class GetRightByValueManagerCommand extends AbstractManagerCommand{
    public static final String PROPERTY_RIGHT = "right";

    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.get-right-by-value:service-is-absence");
    }

    private final String value;

    @Setter
    private RightService rightService;

    public GetRightByValueManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                         final String value) {
        super(consumer);
        this.value = value;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (rightService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<Right> result = rightService.getOneByValue(value);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_RIGHT, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
