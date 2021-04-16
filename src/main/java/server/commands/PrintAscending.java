package server.commands;

import collection.City;
import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Класс для команды print_ascending, которая выводит элементы коллекции в порядке возрастания.
 */

public class PrintAscending extends Command implements Serializable {
    /**
     * Поле, использующееся для временного хранения коллекции.
     */
    private transient final PriorityQueue<City> dop = new PriorityQueue<>(10, Comparator.comparingInt(City::getArea));

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public PrintAscending() {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания", 0, false);
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
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста").append('\n');
        while (!priorityQueue.getCollection().isEmpty()) {
            City city = priorityQueue.pollFromQueue();
            dop.add(city);
        }
        while (!dop.isEmpty()) {
            City city = dop.poll();
            result.append(city.toString()).append('\n');
            priorityQueue.addToCollection(city);
        }
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}
