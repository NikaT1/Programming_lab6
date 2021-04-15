package server.commands;


import client.UserInput;
import collection.InputAndOutput;
import server.collectionUtils.PriorityQueueStorage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс для команды execute_script, которая считывает и исполняет скрипт из указанного файла.
 */

public class ExecuteScript extends Command implements Serializable {
    /**
     * Поле, содержащее список файлов.
     */
    private final HashSet<String> paths;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public ExecuteScript() {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", 1, false);
        paths = new HashSet<>();
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param inputAndOutput  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(InputAndOutput inputAndOutput, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) throws Exception {
       /* try {
            if (!paths.add(inputAndOutput.getArgument())) {
                throw new InvalidAlgorithmParameterException();
            } else {
                String path = inputAndOutput.getArgument();
                FileInputStream fileInputStream = new FileInputStream(path);
                BufferedInputStream file = new BufferedInputStream(fileInputStream);
                Scanner scanner = new Scanner(file);
                Scanner primaryScanner = inputAndOutput.getScanner();
                boolean printMessages = inputAndOutput.getPrintMessages();
                inputAndOutput.setPrintMessages(false);
                inputAndOutput.setScanner(scanner);
                UserInput userInput = new UserInput(inputAndOutput, commandsControl, priorityQueue);
                userInput.input();
                paths.remove(path);
                inputAndOutput.setScanner(primaryScanner);
                inputAndOutput.setPrintMessages(printMessages);
            }
        } catch (FileNotFoundException e) {
            inputAndOutput.output("Файл не существует или не хватает прав на чтение файла");
        }*/
        return null;
    }
}
