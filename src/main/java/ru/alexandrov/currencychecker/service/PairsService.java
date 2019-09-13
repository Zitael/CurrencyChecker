package ru.alexandrov.currencychecker.service;

import java.util.List;

public interface PairsService<T> {
    List<T> getLastN(String symbol, int count);

    List<T> getFilteredByDelta(String symbol, float delta);

    T saveToDB(String symbol, float price);
}
