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

public class DeleteRightByValueManagerCommand extends AbstractManagerCommand {
    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.delete-right-by-value:service-is-absence");
    }

    private final String value;

    @Setter
    private RightService rightService;

    public DeleteRightByValueManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                            final String value) {
        super(consumer);
        this.value = value;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (rightService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<Right> result = rightService.deleteByValue(value);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
