package ru.alexandrov.currencychecker.service;

import java.util.List;

public interface XBTUSDService<T> {
    List<T> getLastN(int count);
    List<T> getFilteredByDelta(float delta);
    T saveToDB(String symbol, float price);
}
