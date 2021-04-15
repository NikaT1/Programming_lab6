package server.commands;

import collection.City;
import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Класс для команды add_if_min, которая добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего
 * элемента этой коллекции.
 */

public class AddIfMin extends Command implements Serializable {
    /**
     * Поле, использующееся для временного хранения коллекции.
     */
    private final PriorityQueue<City> dop = new PriorityQueue<>(10, Comparator.comparingInt(City::getArea));

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public AddIfMin() {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", 0, true);
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
        while (!priorityQueue.getCollection().isEmpty()) {
            City city1 = priorityQueue.pollFromQueue();
            dop.add(city1);
        }
        City city = this.getCity();
        if (dop.peek() != null) {
            if (city.getArea() < dop.peek().getArea()) {
                priorityQueue.addToCollection(city);
                result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
            } else inputAndOutput.output("В коллекцию не добавлен элемент: " + city.toString());
        } else {
            priorityQueue.addToCollection(city);
            result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
        }
        while (!dop.isEmpty()) {
            priorityQueue.addToCollection(dop.poll());
        }
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}
