package server.commands;


import collection.City;
import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

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
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        City city = this.getCity();
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().peek() != null) {
            if (city.getArea() > priorityQueue.getCollection().peek().getArea()) {
                priorityQueue.addToCollection(city);
                result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
            } else inputAndOutput.output("В коллекцию не добавлен элемент: " + city.toString());
        } else {
            priorityQueue.addToCollection(city);
            result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
        }
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}
