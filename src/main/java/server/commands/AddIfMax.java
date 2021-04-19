package server.commands;


import sharedClasses.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;

import java.io.Serializable;

/**
 * Класс для команды add_if_max, которая добавляет новый элемент в коллекцию, если его значение превышает значение
 * наибольшего элемента этой коллекции.
 */

public class AddIfMax extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public AddIfMax() {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", 0, true);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        City city = this.getCity();
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().peek() != null) {
            if (city.getArea() > priorityQueue.getCollection().peek().getArea()) {
                priorityQueue.addToCollection(city);
                result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
            } else result.append("В коллекцию не добавлен элемент: ").append(city.toString());
        } else {
            priorityQueue.addToCollection(city);
            result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
        }
        return Serialization.serializeData(result.toString());
    }
}
