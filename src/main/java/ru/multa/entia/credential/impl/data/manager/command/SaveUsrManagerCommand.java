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

import java.util.function.Consumer;

public class SaveUsrManagerCommand extends AbstractManagerCommand{
    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.save-usr:service-is-absence");
    }

    public static final String PROPERTY_USR = "usr";

    private final Usr usr;

    @Setter
    private UsrService usrService;

    public SaveUsrManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                 final Usr usr) {
        super(consumer);
        this.usr = usr;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (usrService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<Usr> result = usrService.save(usr);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_USR, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
