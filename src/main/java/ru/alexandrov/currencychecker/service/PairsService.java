package ru.alexandrov.currencychecker.service;

import java.util.List;

public interface PairsService<T, K> {
    List<T> getLastNFilteredByDelta(String symbol, int count, float delta);
    T saveToDB(String symbol, float price);
    K getBoxPlotData(String symbol, String from, String to);
}
