package ru.multa.entia.credential.impl.data.manager.command;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.credential.impl.data.manager.item.DefaultManagerDatum;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.function.Consumer;

public class GetUsrByIdManagerCommand extends AbstractManagerCommand{
    public static final String PROPERTY_USR = "usr";

    private final UsrService usrService;
    private final ObjectId id;

    public GetUsrByIdManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                    final UsrService usrService,
                                    final ObjectId id) {
        super(consumer);
        this.usrService = usrService;
        this.id = id;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        Result<Usr> result = usrService.getById(id);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_USR, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
