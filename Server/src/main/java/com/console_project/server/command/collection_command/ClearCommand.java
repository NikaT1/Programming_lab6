package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.storage.Storage;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды clear, которая очищает коллекцию.
 */

@AllArgsConstructor
public class ClearCommand<T> implements Command<T> {

    private final Storage<T> storage;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }
    public CommandResponse execute(UserRequest<T> request) {
        storage.clear();
        return new CommandResponse("OK", "Коллекция успешно очищена");
    }
}
