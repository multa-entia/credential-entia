package ru.multa.entia.credential.impl.data.manager.command;

import lombok.RequiredArgsConstructor;
import ru.multa.entia.credential.api.data.manager.command.ManagerCommand;
import ru.multa.entia.credential.api.data.manager.item.ManagerItem;

import java.util.function.Consumer;

@RequiredArgsConstructor
abstract public class AbstractManagerCommand implements ManagerCommand {
    private final Consumer<ManagerItem> consumer;

    @Override
    public void execute() {
        consumer.accept(executeService());
    }

    protected abstract ManagerItem executeService();
}
