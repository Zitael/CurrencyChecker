package ru.alexandrov.currencychecker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModel;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModelLite;
import ru.alexandrov.currencychecker.dao.repository.XBTUSDRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XBTUSDServiceImpl implements XBTUSDService<XBTUSDModel> {
    private final XBTUSDRepository repository;

    public List<XBTUSDModel> getLastN(int count) {
        return repository.findAll(new PageRequest(0, count, Sort.Direction.DESC, "id")).getContent();
    }

    public List<XBTUSDModel> getFilteredByDelta(float delta) {
        return repository.findAllByDeltaAfter(delta);
    }

    public XBTUSDModel saveToDB(String symbol, float price) {
        XBTUSDModelLite lite = new XBTUSDModelLite();
        lite.setSymbol(symbol);
        lite.setPrice(price);
        return repository.save(convertFromLiteToModel(lite));
    }

    private XBTUSDModel convertFromLiteToModel(XBTUSDModelLite lite) {
        XBTUSDModel result = new XBTUSDModel();
        XBTUSDModel last = repository.findFirstByOrderByIdDesc();
        result.setTimestamp(LocalDateTime.now().toString());
        result.setCurrency("XBTUSD");
        result.setPrevPrice(last == null ? 0 : last.getLastPrice());
        result.setLastPrice(lite.getPrice());
        result.setDelta(Math.abs((int) (result.getLastPrice() - result.getPrevPrice())));
        if (result.getLastPrice() > result.getPrevPrice()) result.setIncreased(1);
        else result.setIncreased(0);
        return result;
    }
}
