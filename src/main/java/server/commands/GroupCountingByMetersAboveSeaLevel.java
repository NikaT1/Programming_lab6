package server.commands;


import collection.City;
import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.PriorityQueue;

/**
 * Класс для команды group_counting_by_meters_above_sea_level, которая группирует элементы коллекции по значению
 * поля metersAboveSeaLevel и выводит количество элементов в каждой группе.
 */

public class GroupCountingByMetersAboveSeaLevel extends Command implements Serializable {
    /**
     * Поле, использующееся для временного хранения коллекции.
     */
    private transient final PriorityQueue<City> dop = new PriorityQueue<>(10, (c1, c2) -> {
        if (c2.getMetersAboveSeaLevel() != null && c1.getMetersAboveSeaLevel() != null) {
            return c1.getMetersAboveSeaLevel().compareTo(c2.getMetersAboveSeaLevel());
        } else if (c2.getMetersAboveSeaLevel() == null && c1.getMetersAboveSeaLevel() != null) {
            return -1;
        } else if (c2.getMetersAboveSeaLevel() != null && c1.getMetersAboveSeaLevel() == null) {
            return 1;
        } else return 0;
    });

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public GroupCountingByMetersAboveSeaLevel() {
        super("group_counting_by_meters_above_sea_level", "сгруппировать элементы коллекции по значению поля metersAboveSeaLevel, вывести количество элементов в каждой группе", 0, false);
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
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста" + '\n');
        else {
            while (!priorityQueue.getCollection().isEmpty()) {
                City city = priorityQueue.pollFromQueue();
                dop.add(city);
            }
            City city = dop.poll();
            Long meters = null;
            if (city != null) {
                meters = city.getMetersAboveSeaLevel();
            }
            result.append("Группа ").append(meters).append(" (м):").append('\n');
            if (city != null) {
                result.append(city.toString()).append('\n');
            } else result.append("null").append('\n');
            priorityQueue.addToCollection(city);
            while (!dop.isEmpty()) {
                city = dop.poll();
                if (meters != null && !meters.equals(city.getMetersAboveSeaLevel()) || meters == null && city.getMetersAboveSeaLevel() != null) {
                    meters = city.getMetersAboveSeaLevel();
                    result.append("Группа ").append(meters).append(" (м):").append('\n');
                }
                result.append(city.toString()).append('\n');
                priorityQueue.addToCollection(city);
            }
        }
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}
