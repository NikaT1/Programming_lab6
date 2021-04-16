package server.commands;


import collection.City;
import collection.InputAndOutput;
import collection.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;

/**
 * Класс для команды average_of_meters_above_sea_level, которая выводит среднее значение поля metersAboveSeaLevel
 * для всех элементов коллекции.
 */

public class AverageOfMetersAboveSeaLevel extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public AverageOfMetersAboveSeaLevel() {
        super("average_of_meters_above_sea_level", "вывести среднее значение поля metersAboveSeaLevel для всех элементов коллекции", 0, false);
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
        if (priorityQueue.getCollection().isEmpty())
            result.append("Коллекция пуста; среднее значение поля metersAboveSeaLevel установить невозможно").append('\n');
        else {
            int sum = 0;
            for (City city : priorityQueue.getCollection()) {
                sum += city.getMetersAboveSeaLevel();
            }
            double answer = sum * 1.0 / priorityQueue.getCollection().size();
            String numberResult = String.format("%.3f", answer);
            result.append("Среднее значение поля metersAboveSeaLevel для всех элементов коллекции: ").append(numberResult).append("\n");
        }
        return Serialization.serializeData(result.toString());
    }
}
