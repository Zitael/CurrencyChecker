package ru.alexandrov.currencychecker.service.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.alexandrov.currencychecker.dao.model.PairsModelLite;
import ru.alexandrov.currencychecker.service.client.ClientService;

@Service
public class BitMexClientService implements ClientService<PairsModelLite> {
    private RestTemplate restTemplate = new RestTemplate();
    private final static String url = "https://www.bitmex.com/api/v1";
    private final static String endpoint = "/instrument";

    @Override
    public PairsModelLite[] getPairs(final String symbol) {
        return restTemplate.getForObject(buildUrl(symbol), PairsModelLite[].class);
    }

    private String buildUrl(String symbol){
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(endpoint);
        sb.append("?symbol=");
        sb.append(symbol);
        sb.append("&columns=midPrice");
        return sb.toString();
    }
}
