package ru.alexandrov.currencychecker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.repository.PairsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PairsServiceImpl implements PairsService<PairsModel> {
    private final PairsRepository repository;

    public List<PairsModel> getLastN(String symbol, int count) {
        return repository.findAllByCurrency(symbol.toUpperCase(), new PageRequest(0, count, Sort.Direction.DESC, "id")).getContent();
    }

    public List<PairsModel> getFilteredByDelta(String symbol, float delta) {
        return repository.findAllByCurrencyAndDeltaAfter(symbol.toUpperCase(), delta);
    }

    public PairsModel saveToDB(String symbol, float price) {
        PairsModel result = new PairsModel();
        PairsModel last = repository.findFirstByCurrencyOrderByIdDesc(symbol.toUpperCase());
        result.setTimestamp(LocalDateTime.now().toString());
        result.setCurrency(symbol.toUpperCase());
        result.setPrevPrice(last == null ? 0 : last.getLastPrice());
        result.setLastPrice(price);
        result.setDelta(Math.abs((int) (result.getLastPrice() - result.getPrevPrice())));
        if (result.getLastPrice() > result.getPrevPrice()) result.setIncreased(1);
        else result.setIncreased(0);
        return repository.save(result);
    }
}
