package server.commands;


import server.IOForClient;
import sharedClasses.City;
import sharedClasses.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Класс для команды add, которая добавляет новый элемент в коллекцию.
 */

public class Add extends Command {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Add() {
        super("add", "добавить новый элемент в коллекцию", 0, true);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder();
        try {
            City city = this.getCity();
            city.setId(priorityQueue.generateId());
            priorityQueue.addToCollection(city);
            result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
        } catch (IllegalStateException e) {
            result.append("Ошибка: в коллекции слишком много элементов; объект коллекции не создан");
        } catch (NoSuchElementException e) {
            result.append("В скрипте не указаны значения для создания элемента коллекции. Команда add не выполнена");
        }
        return Serialization.serializeData(result.toString());
    }
}
