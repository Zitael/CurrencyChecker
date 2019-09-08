package ru.alexandrov.currencychecker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.controller.RequestToBitMexMaker;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModelLite;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataRefresher {
    private final XBTUSDService service;
    private final RequestToBitMexMaker rm;
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @Scheduled(cron = "*/10 * * * * *")
    public void refreshData() {
        String url = "https://www.bitmex.com/api/v1/instrument?symbol=XBTUSD&columns=midPrice";
        try {
            List<XBTUSDModelLite> results = mapper.readValue(rm.doGetByUrl(url), new TypeReference<List<XBTUSDModelLite>>() {
            });
            service.saveToDB(results.get(0).getSymbol(), results.get(0).getPrice());
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException("Error refreshing data", e);
        }
    }
}
