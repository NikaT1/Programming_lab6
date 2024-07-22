package com.console_project.server.storage;

import com.console_project.server.exception.TooMuchElementsException;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Интерфейс для классов, которые управляют доступом к коллекции.
 *
 * @param <T> тип элементов коллекции.
 */
public interface Storage<T> {

    LocalDate getCreationDate();

    Stream<T> getCollectionStream();

    void addElement(T t) throws TooMuchElementsException;

    boolean deleteById(int id);

    int getSize();

    T removeHead();

    boolean updateById(int id, T t);

    boolean addElementIfMax(T t) throws TooMuchElementsException;

    boolean addElementIfMin(T t) throws TooMuchElementsException;

    double averageOfMetersAboveSeaLevel();

    void clear();

    Map<Long, Long> groupCountingByMetersAboveSeaLevel();

    Stream<T> getCollectionSortedStream();
}
