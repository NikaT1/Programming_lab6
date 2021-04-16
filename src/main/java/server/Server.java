package server;

import collection.InputAndOutput;
import collection.Serialization;
import server.collectionUtils.Parser;
import server.collectionUtils.PriorityQueueStorage;
import server.commands.Command;
import server.commands.CommandsControl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private String[] args;
    private final InputAndOutput inputAndOutput;
    private DatagramSocket datagramSocket;
    private final CommandsControl commandsControl;
    private final Serialization serialization;
    private PriorityQueueStorage priorityQueue;
    private IOForClient ioForClient;

    public Server() {
        inputAndOutput = new InputAndOutput(new Scanner(System.in), true);
        commandsControl = new CommandsControl();
        serialization = new Serialization();
        ioForClient = new IOForClient(new Scanner(System.in), true);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.args = args;
        server.run();
    }

    public void run() {
        FileInputStream fileInputStream = null;
        try {
            Pattern pattern = Pattern.compile("/dev/*");
            Matcher matcher = pattern.matcher(args[0]);
            if (matcher.find()) {
                inputAndOutput.output("Вы задали недопустимое имя файла");
                System.exit(1);
            }
            fileInputStream = new FileInputStream(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            inputAndOutput.output("Вы не задали имя файла");
            System.exit(1);
        } catch (
                FileNotFoundException e) {
            inputAndOutput.output("Файл не существует или не хватает прав на чтение");
            System.exit(1);
        }
        Parser parser;
        try {
            priorityQueue = new PriorityQueueStorage(args[0]);
            parser = new Parser(priorityQueue);
            BufferedInputStream file = new BufferedInputStream(fileInputStream);
            InputStreamReader lines = new InputStreamReader(file, StandardCharsets.UTF_8);
            parser.parseFile(lines);
            inputAndOutput.output("Коллекция успешно создана!");
        } catch (NumberFormatException e) {
            inputAndOutput.output("Значения полей объектов введены неверно");
            System.exit(1);
        } catch (NullPointerException e) {
            inputAndOutput.output("Файл сожержит не все поля, необходимые для создания элементов коллекции");
            System.exit(1);
        } catch (ParseException e) {
            inputAndOutput.output("Дата в файле введена в неправильном формате");
            System.exit(1);
        } catch (Exception e) {
            inputAndOutput.output("Ошибка при чтении из файла");
            e.printStackTrace();
            System.exit(1);
        }
        try {
            new InetSocketAddress("localhost", 666);
            datagramSocket = new DatagramSocket(666);
            ioForClient.setDatagramSocket(datagramSocket);
            while (true) {
                this.execute();
            }
        } catch (SocketException e) {
            inputAndOutput.output("Возникли проблемы с подключением");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute() throws Exception {
        byte[] bytes = new byte[100000];
        bytes = ioForClient.input(bytes);
        Command command = (Command) serialization.deserializeData(bytes);
        byte[] commandResult = command.doCommand(inputAndOutput, commandsControl, priorityQueue);
        ioForClient.output(commandResult);
    }
}
