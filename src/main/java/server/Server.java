package server;

import server.collectionUtils.Parser;
import server.collectionUtils.PriorityQueueStorage;
import server.commands.Command;
import server.commands.CommandsControl;
import server.commands.ExecuteScript;
import sharedClasses.IOForClient;
import sharedClasses.Serialization;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private String[] args;
    private final CommandsControl commandsControl;
    private final Serialization serialization;
    private PriorityQueueStorage priorityQueue;
    private final IOForClient ioForClient;

    public Server() {
        commandsControl = new CommandsControl();
        serialization = new Serialization();
        ioForClient = new IOForClient(true);
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
                //inputAndOutput.output("Вы задали недопустимое имя файла");
                System.exit(1);
            }
            fileInputStream = new FileInputStream(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            //inputAndOutput.output("Вы не задали имя файла");
            System.exit(1);
        } catch (FileNotFoundException e) {
            //inputAndOutput.output("Файл не существует или не хватает прав на чтение");
            System.exit(1);
        }
        Parser parser;
        try {
            priorityQueue = new PriorityQueueStorage(args[0]);
            parser = new Parser(priorityQueue);
            BufferedInputStream file = new BufferedInputStream(fileInputStream);
            InputStreamReader lines = new InputStreamReader(file, StandardCharsets.UTF_8);
            parser.parseFile(lines);
            //inputAndOutput.output("Коллекция успешно создана!");
        } catch (NumberFormatException e) {
            //inputAndOutput.output("Значения полей объектов введены неверно");
            System.exit(1);
        } catch (NullPointerException e) {
            //inputAndOutput.output("Файл сожержит не все поля, необходимые для создания элементов коллекции");
            System.exit(1);
        } catch (ParseException e) {
            //inputAndOutput.output("Дата в файле введена в неправильном формате");
            System.exit(1);
        } catch (Exception e) {
            //inputAndOutput.output("Ошибка при чтении из файла");
            e.printStackTrace();
            System.exit(1);
        }
        try {
            new InetSocketAddress("localhost", 666);
            DatagramSocket datagramSocket = new DatagramSocket(666);
            ioForClient.setDatagramSocket(datagramSocket);
            while (true) {
                this.execute();
            }
        } catch (PortUnreachableException e) {
            //ioForClient.output("Возникли проблемы с доступом к порту");
        } catch (SocketException e) {
            //inputAndOutput.output("Возникли проблемы с подключением");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute() throws Exception {
        try {
            byte[] bytes = new byte[100000];
            bytes = ioForClient.input(bytes);
            Command command = (Command) serialization.deserializeData(bytes);
            byte[] commandResult = command.doCommand(ioForClient, commandsControl, priorityQueue);
            ioForClient.output(commandResult);
        } catch (InvalidAlgorithmParameterException e) {
            ioForClient.output(e.getMessage());
        } catch (NoSuchElementException | NumberFormatException | ParseException e) {
            ExecuteScript command = (ExecuteScript) commandsControl.getCommands().get("execute_script");
            command.getPaths().clear();
            ioForClient.output("Неверный формат введенных данных");
        }
    }
}
