package ru.multa.entia.credential.impl.data.manager.command;

import lombok.Setter;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.credential.impl.data.manager.item.DefaultManagerDatum;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.List;
import java.util.function.Consumer;

public class GetUsrByPaterNameManagerCommand extends AbstractManagerCommand {
    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.get-usr-by-pater-name:service-is-absence");
    }

    public static final String PROPERTY_USERS = "users";

    private final String paterName;

    @Setter
    private UsrService usrService;

    public GetUsrByPaterNameManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                           final String paterName) {
        super(consumer);
        this.paterName = paterName;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (usrService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<List<Usr>> result = usrService.getByPaterName(paterName);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_USERS, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
