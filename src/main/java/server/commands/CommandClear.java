package server.commands;


import collection.InputAndOutput;
import collection.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;

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
        return Serialization.serializeData("Коллекция успешно очищена");
    }
}
