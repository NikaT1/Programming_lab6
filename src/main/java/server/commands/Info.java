package server.commands;


import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Класс для команды info, которая выводит в стандартный поток вывода информацию о коллекции.
 */

public class Info extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Info() {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        String result = "тип: PriorityQueue" + '\n' + "дата инициализации: " + priorityQueue.getCreationDate() + '\n' +
                "количество элементов: " + priorityQueue.getCollection().size() + '\n';
        return result.getBytes(StandardCharsets.UTF_8);
    }
}
