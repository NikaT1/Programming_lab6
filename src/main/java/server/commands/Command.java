package server.commands;


import sharedClasses.IOForClient;
import sharedClasses.City;
import sharedClasses.Serialization;
import server.collectionUtils.PriorityQueueStorage;
import java.io.Serializable;

/**
 * Абстрактный класс для всех команд.
 */

public abstract class Command implements Serializable{
    /**
     * Дополнительная информация о команде.
     */
    private final String someInformation;
    /**
     * Название команды.
     */
    private final String name;
    private final int amountOfArguments;
    private String argument;
    private City city;
    private final boolean needCity;
    private transient final Serialization serialization = new Serialization();

    /**
     * Конструктор.
     *
     * @param name            название команды.
     * @param someInformation дополнительная информация о команде.
     */
    public Command(String name, String someInformation, int amountOfArguments, boolean needCity ) {
        this.name = name;
        this.someInformation = someInformation;
        this.amountOfArguments = amountOfArguments;
        this.needCity = needCity;
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient  объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     * @throws Exception в случае ошибки при выполнении команды.
     */
    public abstract byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) throws Exception;

    /**
     * Метод, возвращающий название команды.
     *
     * @return название команды.
     */
    public String getName() {
        return name;
    }
    public Serialization getSerialization() {
        return serialization;
    }
    /**
     * Метод, возвращающий информацию о команде.
     *
     * @return информация о команде.
     */
    public String getSomeInformation() {
        return someInformation;
    }

    public int getAmountOfArguments() {
        return amountOfArguments;
    }

    public String getArgument() {
        return argument;
    }

    public City getCity() {
        return city;
    }

    public boolean isNeedCity() {
        return needCity;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Метод, возвращающий объект в строковом представлении.
     *
     * @return объект в строковом представлении.
     */
    public String toString() {
        return getName() + " : " + getSomeInformation();
    }
}
