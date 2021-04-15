package server.commands;


import collection.City;
import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.PriorityQueue;

/**
 * Класс для команды remove_by_id, которая удаляет элемент из коллекции по его id.
 */

public class RemoveById extends Command implements Serializable {
    /**
     * Поле, использующееся для временного хранения коллекции.
     */
    private final PriorityQueue<City> dop = new PriorityQueue<>(10, (c1, c2) -> (c2.getArea() - c1.getArea()));

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveById() {
        super("remove_by_id id", "удалить элемент из коллекции по его id", 1, false);
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
        try {
            int id = Integer.parseInt(this.getArgument());
            boolean flag = false;
            while (!priorityQueue.getCollection().isEmpty()) {
                City city = priorityQueue.pollFromQueue();
                if (city.getId() == id) {
                    flag = true;
                } else dop.add(city);
            }
            if (flag) result.append("удаление элемента успешно завершено");
            else result.append("Элемент с id " + id + " не существует");
            while (!dop.isEmpty()) {
                City city = dop.poll();
                priorityQueue.addToCollection(city);
            }
        } catch (NumberFormatException e) {
            result.append("неправильный формат id");
        }
        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}
