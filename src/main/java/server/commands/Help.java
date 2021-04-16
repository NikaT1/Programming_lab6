package server.commands;


import collection.InputAndOutput;
import collection.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;

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
        return Serialization.serializeData(result.toString());
    }
}
