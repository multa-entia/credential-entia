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
public class GetUsrByFirstNameManagerCommand extends AbstractManagerCommand{
    public static final String PROPERTY_USERS = "users";

    private final UsrService usrService;
    private final String firstName;

    public GetUsrByFirstNameManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                           final UsrService usrService,
                                           final String firstName) {
        super(consumer);
        this.usrService = usrService;
        this.firstName = firstName;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        Result<List<Usr>> result = usrService.getByFirstName(firstName);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_USERS, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
