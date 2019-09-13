package ru.alexandrov.currencychecker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.PairsModelLite;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataRefresher {
    private final PairsService service;
    private final ClientService<PairsModelLite> clientService;

    @Scheduled(cron = "*/10 * * * * *")
    public void refreshData() {
        PairsModelLite[] result = clientService.getPairs();
        if (result != null && result.length > 0) {
            for (PairsModelLite lite : result) {
                service.saveToDB(lite.getSymbol(), lite.getPrice());
            }
        } else log.error("Error refreshing data");
    }
}
