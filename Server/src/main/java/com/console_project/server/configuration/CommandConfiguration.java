package com.console_project.server.configuration;

import com.console_project.server.command.Command;
import com.console_project.server.command.CommandInvoker;
import com.console_project.server.command.ExitHandler;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.command.collection_command.*;
import com.console_project.server.file_util.FileReaderWriter;
import com.console_project.server.storage.Storage;
import com.console_project.shared.connection_model.UserRequest;
import com.console_project.shared.model.City;

public class CommandConfiguration {
    public static CommandInvoker<City> getCommandsInvoker(Storage<City> storage, FileReaderWriter<City> fileReaderWriter) {
        CommandInvoker<City> commandInvoker = new CommandInvoker<>();
        commandInvoker.registerCommand(new AddCommand<>(storage, TypeOfCommand.ADD));
        commandInvoker.registerCommand(new AddIfMaxCommand<>(storage, TypeOfCommand.ADD_IF_MAX));
        commandInvoker.registerCommand(new AddIfMinCommand<>(storage, TypeOfCommand.ADD_IF_MIN));
        commandInvoker.registerCommand(new AverageOfMetersAboveSeaLevelCommand<>(storage,
                TypeOfCommand.AVERAGE_OF_METERS_ABOVE_SEA_LEVEL));
        commandInvoker.registerCommand(new ClearCommand<>(storage, TypeOfCommand.CLEAR));
        commandInvoker.registerCommand(new ExecuteScriptCommand<>(storage, TypeOfCommand.EXECUTE_SCRIPT));
        commandInvoker.registerCommand(new GroupCountingByMetersAboveSeaLevelCommand<>(storage,
                TypeOfCommand.GROUP_COUNTING_BY_METERS_ABOVE_SEA_LEVEL));
        commandInvoker.registerCommand(new HelpCommand<>(TypeOfCommand.HELP));
        commandInvoker.registerCommand(new InfoCommand<>(storage, TypeOfCommand.INFO));
        commandInvoker.registerCommand(new PrintAscendingCommand<>(storage, TypeOfCommand.PRINT_ASCENDING));
        commandInvoker.registerCommand(new RemoveByIdCommand<>(storage, TypeOfCommand.REMOVE_BY_ID));
        commandInvoker.registerCommand(new RemoveHeadCommand<>(storage, TypeOfCommand.REMOVE_HEAD));
        commandInvoker.registerCommand(new SaveCommand<>(fileReaderWriter, storage,
                TypeOfCommand.SAVE));
        commandInvoker.registerCommand(new ShowCommand<>(storage, TypeOfCommand.SHOW));
        commandInvoker.registerCommand(new UpdateIdCommand<>(storage, TypeOfCommand.UPDATE_ID));
        return commandInvoker;
    }

    public static ExitHandler getExitHandler(CommandInvoker commandInvoker) {
        return new ExitHandler(commandInvoker.getCommand(TypeOfCommand.SAVE),
                new UserRequest("", "save", ""));
    }
}
