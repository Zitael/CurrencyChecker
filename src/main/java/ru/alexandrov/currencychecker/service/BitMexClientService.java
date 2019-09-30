package ru.alexandrov.currencychecker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.alexandrov.currencychecker.dao.model.PairsModelLite;

@Service
public class BitMexClientService implements ClientService<PairsModelLite> {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public PairsModelLite[] getPairs(final String symbol) {
        String url = "https://www.bitmex.com/api/v1/instrument?symbol="
                + symbol
                + "&columns=midPrice";
        return restTemplate.getForObject(url, PairsModelLite[].class);
    }
}
