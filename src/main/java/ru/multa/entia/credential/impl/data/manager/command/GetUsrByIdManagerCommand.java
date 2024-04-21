package ru.multa.entia.credential.impl.data.manager.command;

import lombok.Setter;
import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.credential.api.data.usr.Usr;
import ru.multa.entia.credential.api.data.usr.UsrService;
import ru.multa.entia.credential.impl.data.manager.item.DefaultManagerDatum;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.function.Consumer;


public class GetUsrByIdManagerCommand extends AbstractManagerCommand{
    public enum Code {
        SERVICE_IS_ABSENCE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.SERVICE_IS_ABSENCE, "credential:command.get-usr-by-id:service-is-absence");
    }

    public static final String PROPERTY_USR = "usr";

    private final ObjectId id;

    @Setter
    private UsrService usrService;

    public GetUsrByIdManagerCommand(final Consumer<Result<ManagerDatum>> consumer,
                                    final ObjectId id) {
        super(consumer);
        this.id = id;
    }

    @Override
    protected Result<ManagerDatum> executeService() {
        if (usrService == null) {
            return DefaultResultBuilder.<ManagerDatum>fail(CR.get(Code.SERVICE_IS_ABSENCE));
        }

        Result<Usr> result = usrService.getById(id);
        return result.ok()
                ? DefaultResultBuilder.<ManagerDatum>ok(DefaultManagerDatum.builder().property(PROPERTY_USR, result.value()).build())
                : DefaultResultBuilder.<ManagerDatum>fail(result.seed());
    }
}
