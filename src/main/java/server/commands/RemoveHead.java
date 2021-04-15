package server.commands;


import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Класс для команды remove_head, которая выводит и удаляет первый элемент из коллекции.
 */

public class RemoveHead extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveHead() {
        super("remove_head", "вывести первый элемент коллекции и удалить его", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста");
        else {
            result.append(priorityQueue.pollFromQueue().toString()).append("удаление элемента успешно завершено");
        }
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}
