package ru.alexandrov.currencychecker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModelLite;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataRefresher {
    private final XBTUSDService service;
    private String bitMexUrl = "https://www.bitmex.com/api/v1/instrument?symbol=XBTUSD&columns=midPrice";
    private RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "*/10 * * * * *")
    public void refreshData() {
        XBTUSDModelLite[] result = restTemplate.getForObject(bitMexUrl, XBTUSDModelLite[].class);
        if (result != null && result[0] != null) {
            service.saveToDB(result[0].getSymbol(), result[0].getPrice());
        } else log.error("Error refreshing data");
    }
}
