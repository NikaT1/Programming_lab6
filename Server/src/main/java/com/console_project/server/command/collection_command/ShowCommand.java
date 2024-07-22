package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.storage.Storage;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

/**
 * Класс для команды show, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */

@AllArgsConstructor
public class ShowCommand<T> implements Command<T> {

    private final Storage<T> storage;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    public CommandResponse execute(UserRequest<T> request) {
        String result = storage.getCollectionStream()
                .map(Object::toString)
                .collect(Collectors.joining(";\n"));
        return new CommandResponse("OK", result);
    }
}
