package ru.alexandrov.currencychecker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModelLite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataRefresher {
    private final XBTUSDService service;
    private String bitMexUrl = "https://www.bitmex.com/api/v1/instrument?symbol=XBTUSD&columns=midPrice";
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @Scheduled(cron = "*/10 * * * * *")
    public void refreshData() {
        try {
            List<XBTUSDModelLite> results = mapper.readValue(doRequestByUrl(bitMexUrl), new TypeReference<List<XBTUSDModelLite>>() {
            });
            service.saveToDB(results.get(0).getSymbol(), results.get(0).getPrice());
        } catch (Exception e) {
            log.error("Error refreshing data", e);
        }
    }
    private String doRequestByUrl(String url) throws Exception {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            log.error("Error connecting to Exchange", e);
            throw new Exception(e);
        }
    }
}
