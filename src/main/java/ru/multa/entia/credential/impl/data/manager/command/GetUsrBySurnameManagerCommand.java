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

public class GetUsrBySurnameManagerCommand extends AbstractManagerCommand{
    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.get-usr-by-surname:service-is-absence");
    }

    public static final String PROPERTY_USERS = "users";

    private final String surname;

    @Setter
    private UsrService usrService;

    public GetUsrBySurnameManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                         final String surname) {
        super(consumer);
        this.surname = surname;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (usrService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<List<Usr>> result = usrService.getBySurname(surname);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_USERS, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
