package ru.alexandrov.currencychecker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModel;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModelLite;
import ru.alexandrov.currencychecker.dao.repository.XBTUSDRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class XBTUSDService {
    private final XBTUSDRepository repository;
    private float prevPrice;

    public XBTUSDModel saveToDB(String symbol, float price) {
        XBTUSDModelLite lite = new XBTUSDModelLite();
        lite.setSymbol(symbol);
        lite.setPrice(price);
        return repository.save(convertFromLiteToModel(lite));
    }

    public XBTUSDModel convertFromLiteToModel(XBTUSDModelLite lite) {
        XBTUSDModel result = new XBTUSDModel();
        result.setTimestamp(LocalDateTime.now().toString());
        result.setCurrency("XBTUSD");
        if (prevPrice == 0) prevPrice = lite.getPrice();
        else {
            result.setPrevPrice(prevPrice);
            prevPrice = lite.getPrice();
        }
        result.setLastPrice(lite.getPrice());
        result.setDelta(Math.abs((int) (result.getLastPrice() - result.getPrevPrice())));
        if (result.getLastPrice() > result.getPrevPrice()) result.setIncreased(1);
        else result.setIncreased(0);
        return result;
    }
}
