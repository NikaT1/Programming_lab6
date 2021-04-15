package client;

import collection.City;
import collection.Climate;
import collection.Coordinates;
import collection.Human;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;

import collection.InputAndOutput;

public class UserInput {

    InputAndOutput inputAndOutput;

    public UserInput(InputAndOutput inputAndOutput) {
        this.inputAndOutput = inputAndOutput;
    }

    /**
     * Метод, считывающий значение поля name.
     *
     * @return значение поля name.
     */
    private String readName() {
        boolean flag = false;
        String name = null;
        while (!flag) {
            flag = true;
            name = readField("Введите название города:");
            if (name.equals("")) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return name;
    }

    /**
     * Метод, считывающий значение одного поля.
     *
     * @param message сообщение пользователю.
     * @return значение поля.
     */
    public String readField(String message) {
        String s;
        if (inputAndOutput.getPrintMessages()) System.out.println(message);
        s = inputAndOutput.getScanner().nextLine();
        return s;
    }

    /**
     * Метод, считывающий значение поля area.
     *
     * @return значение поля area.
     */
    private int readArea() {
        boolean flag = false;
        int area = 1;
        while (!flag) {
            flag = true;
            try {
                area = Integer.parseInt(readField("Введите размер территории (в квадратных метрах):"));
                if (area <= 0) {
                    inputAndOutput.output("Значение меньше допустимого, повторите ввод:");
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return area;
    }

    /**
     * Метод, считывающий значение поля population.
     *
     * @return значение поля population.
     */
    private long readPopulation() {
        boolean flag = false;
        long population = 1;
        while (!flag) {
            flag = true;
            try {
                population = Long.parseLong(readField("Введите численность населения:"));
                if (population <= 0) {
                    inputAndOutput.output("Значение меньше допустимого, повторите ввод:");
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return population;
    }

    /**
     * Метод, считывающий значение поля metersAboveSeaLevel.
     *
     * @return значение поля metersAboveSeaLevel.
     */
    private Long readMetersAboveSeaLevel() {
        boolean flag = false;
        Long metersAboveSeaLevel = null;
        while (!flag) {
            flag = true;
            String s = readField("Введите количество метров над уровнем моря:");
            if (s.equals("")) {
                return null;
            }
            try {
                metersAboveSeaLevel = Long.parseLong(s);
            } catch (NumberFormatException ex) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return metersAboveSeaLevel;
    }

    /**
     * Метод, считывающий значение поля establishmentDate.
     *
     * @return значение поля establishmentDate.
     */
    private Date readEstablishmentDate() {
        boolean flag = false;
        Date establishmentDate = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        while (!flag) {
            flag = true;
            String s = readField("Введите дату создания (yyyy-MM-dd):");
            if (s.equals("")) {
                return null;
            }
            try {
                establishmentDate = formatter.parse(s);
            } catch (ParseException e) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return establishmentDate;
    }

    /**
     * Метод, считывающий значение поля agglomeration.
     *
     * @return значение поля agglomeration.
     */
    private Integer readAgglomeration() {
        boolean flag = false;
        Integer agglomeration = null;
        while (!flag) {
            flag = true;
            String s = readField("Введите размер агломерации (в квадратных метрах):");
            if (s.equals("")) {
                return null;
            }
            try {
                agglomeration = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return agglomeration;
    }

    /**
     * Метод, считывающий значение поля climate.
     *
     * @return значение поля climate.
     */
    private Climate readClimate() {
        boolean flag = false;
        Climate climate = null;
        while (!flag) {
            flag = true;
            try {
                climate = Climate.valueOf(readField("Введите тип климата (RAIN_FOREST, MONSOON, HUMIDSUBTROPICAL):"));
            } catch (IllegalArgumentException ex) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return climate;
    }

    /**
     * Метод, считывающий значение поля governor.
     *
     * @return значение поля governor.
     */
    private Human readGovernor() {
        boolean flag = false;
        Integer age = null;
        while (!flag) {
            flag = true;
            String s = readField("Введите возраст губернатора:");
            if (s.equals("")) {
                return new Human(null);
            }
            try {
                age = Integer.parseInt(s);
                if (age <= 0) {
                    inputAndOutput.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return new Human(age);
    }

    /**
     * Метод, считывающий и создающий объект класса City.
     *
     * @return новый объект класса City.
     */
    public City readCity() throws NumberFormatException, NoSuchElementException {
        City city = new City();
        city.setName(readName());
        city.setCoordinates(readCoordinates());
        city.setCreationDate(LocalDate.now());
        city.setArea(readArea());
        city.setPopulation(readPopulation());
        city.setMetersAboveSeaLevel(readMetersAboveSeaLevel());
        city.setEstablishmentDate(readEstablishmentDate());
        city.setAgglomeration(readAgglomeration());
        city.setClimate(readClimate());
        city.setGovernor(readGovernor());
        return city;
    }

    /**
     * Метод, считывающий значение поля coordinates.
     *
     * @return значение поля coordinates.
     */
    public Coordinates readCoordinates() {
        boolean flag = false;
        Float x = null;
        Integer y = null;
        while (!flag) {
            flag = true;
            try {
                x = Float.parseFloat(readField("Введите координату х:"));
                if (x <= -724) {
                    inputAndOutput.output("Значение меньше допустимого, повторите ввод:");
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        flag = false;
        while (!flag) {
            flag = true;
            try {
                y = Integer.parseInt(readField("Введите координату у:"));
                if (y <= -989) {
                    inputAndOutput.output("Значение меньше допустимого, повторите ввод:");
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                inputAndOutput.output("Неверный формат данных, повторите ввод:");
                flag = false;
            }
        }
        return new Coordinates(x, y);
    }
}
