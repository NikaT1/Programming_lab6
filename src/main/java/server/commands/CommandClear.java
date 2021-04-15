package server.commands;


import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Класс для команды clear, которая очищает коллекцию.
 */

public class CommandClear extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandClear() {
        super("clear", "очистить коллекцию", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        priorityQueue.getCollection().clear();
        return "Коллекция успешно очищена".getBytes(StandardCharsets.UTF_8);
    }
}
