package ru.multa.entia.credential.impl.data.manager.command;

import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.credential.impl.data.manager.item.DefaultManagerDatum;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.List;
import java.util.function.Consumer;

// TODO: impl service setter
public class GetUsrBySurnameManagerCommand extends AbstractManagerCommand{
    public static final String PROPERTY_USERS = "users";

    private final UsrService usrService;
    private final String surname;

    public GetUsrBySurnameManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                         final UsrService usrService,
                                         final String surname) {
        super(consumer);
        this.usrService = usrService;
        this.surname = surname;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        Result<List<Usr>> result = usrService.getBySurname(surname);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_USERS, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
