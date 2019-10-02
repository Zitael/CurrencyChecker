package ru.alexandrov.currencychecker.service.datarefresher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.PairsModelLite;
import ru.alexandrov.currencychecker.service.MyException;
import ru.alexandrov.currencychecker.service.client.ClientService;
import ru.alexandrov.currencychecker.service.pairs.PairsService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataRefresher {
    private final PairsService service;
    private final ClientService<PairsModelLite> clientService;
    private final List<String> symbolsToRefresh = Collections.singletonList("XBTUSD");

    @Scheduled(cron = "*/10 * * * * *")
    public void refreshData() {
        for (String symbol : symbolsToRefresh) {
            PairsModelLite[] result = clientService.getPairs(symbol);
            if (result != null && result.length > 0) {
                for (PairsModelLite lite : result) {
                    try {
                        service.saveToDB(lite.getSymbol(), lite.getPrice(), lite.getTimestamp());
                    } catch (MyException e) {
                        log.error("Cant save data, " + e.getMessage());
                    }
                }
            } else log.error("Error refreshing data");
        }
    }
}
