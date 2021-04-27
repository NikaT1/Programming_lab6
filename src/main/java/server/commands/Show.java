package server.commands;

import server.IOForClient;
import sharedClasses.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
/**
 * Класс для команды show, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */

public class Show extends Command implements Serializable {

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Show() {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста").append('\n');
        else priorityQueue.getCollection().forEach(city -> result.append(city.toString()).append('\n'));
        result.delete(result.length()-1, result.length());
        return Serialization.serializeData(result.toString());
    }
}