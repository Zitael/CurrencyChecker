package ru.alexandrov.currencychecker.service.pairs;

import ru.alexandrov.currencychecker.service.MyException;

import java.math.BigDecimal;
import java.util.List;

public interface PairsService<T, K> {
    List<T> getLastNFilteredByDelta(String symbol, int count, float delta) throws MyException;
    T saveToDB(String symbol, BigDecimal price);
    K getBoxPlotData(String symbol, String from, String to) throws MyException;
}
