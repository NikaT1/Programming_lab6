package server.commands;


import collection.City;
import collection.InputAndOutput;
import collection.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * Класс для команды update, которая обновляет значение элемента коллекции по его id.
 */

public class UpdateId extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public UpdateId() {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному", 1, true);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        PriorityQueue<City> dop = new PriorityQueue<>(10, (c1, c2) -> (c2.getArea() - c1.getArea()));
        StringBuilder result = new StringBuilder();
        try {
            int id = Integer.parseInt(getArgument());
            boolean flag = false;
            while (!priorityQueue.getCollection().isEmpty()) {
                City city = priorityQueue.pollFromQueue();
                if (city.getId() == id) {
                    city = getCity();
                    flag = true;
                }
                dop.add(city);
            }
            if (flag) result.append("обновление элемента успешно завершено");
            else result.append("Элемент с id ").append(id).append(" не существует");
            while (!dop.isEmpty()) {
                City city = dop.poll();
                priorityQueue.addToCollection(city);
            }
        } catch (NumberFormatException e) {
            result.append("неправильный формат id");
        }
        return Serialization.serializeData(result.toString());
    }
}
