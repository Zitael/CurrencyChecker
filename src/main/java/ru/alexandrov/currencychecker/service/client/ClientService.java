package ru.alexandrov.currencychecker.service.client;

public interface ClientService<T> {
    T[] getPairs(String symbol);
}
