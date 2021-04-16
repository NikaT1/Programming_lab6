package server.commands;


import collection.InputAndOutput;
import collection.Serialization;
import server.collectionUtils.PriorityQueueStorage;

import java.io.Serializable;

/**
 * Класс для команды exit, которая завершает программу без сохранения в файл коллекции.
 */

public class CommandExit extends Command implements Serializable {
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandExit() {
        super("exit", "завершить программу (без сохранения в файл)", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        String result = "Коллекция сохранена в файл, работа приложения завершается";
        try {
            commandsControl.getCommands().get("save").doCommand(inputAndOutput,commandsControl,priorityQueue);
        } catch (Exception e) {
            result = "Возникла ошибка при сохранении коллекции";
        }
        return Serialization.serializeData(result);
    }
}
