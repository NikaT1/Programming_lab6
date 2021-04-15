package server.commands;


import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Класс для команды help, которая выводит справку по доступным коммандам.
 */

public class Help extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Help() {
        super("help", "вывести справку по доступным командам", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder("Доступные команды:" + '\n');
        for (Command command : commandsControl.getCommands().values()) {
            result.append(command.toString()).append('\n');
        }
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}
