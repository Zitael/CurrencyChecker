package ru.alexandrov.currencychecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CurrencycheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencycheckerApplication.class, args);
    }

}
