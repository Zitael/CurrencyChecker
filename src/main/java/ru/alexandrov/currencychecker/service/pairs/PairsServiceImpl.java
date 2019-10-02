package ru.alexandrov.currencychecker.service.pairs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.BoxPlotData;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.repository.PairsRepository;
import ru.alexandrov.currencychecker.service.calculation.CalculationService;
import ru.alexandrov.currencychecker.service.MyException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PairsServiceImpl implements PairsService<PairsModel, BoxPlotData> {
    private final PairsRepository repository;
    private final CalculationService calculationService;

    @Override
    public List<PairsModel> getLastNFilteredByDelta(String symbol, int count, float delta) throws MyException {
        List<PairsModel> result = repository.findAllByCurrencyAndDeltaAfter(symbol.toUpperCase(), delta, count);
        if (result == null || result.isEmpty()){
            throw new MyException("Not found");
        }
        return result;
    }

    @Override
    public PairsModel saveToDB(String symbol, BigDecimal price) {
        PairsModel result = new PairsModel();
        PairsModel last = repository.findFirstByCurrencyOrderByIdDesc(symbol.toUpperCase());
        result.setTimestamp(System.currentTimeMillis());
        result.setCurrency(symbol.toUpperCase());
        result.setLastPrice(price);
        if (last == null) {
            result.setPrevPrice(null);
            result.setDelta(null);
            result.setIncreased(false);
        } else {
            result.setPrevPrice(last.getLastPrice());
            result.setDelta(result.getLastPrice().subtract(result.getPrevPrice()).abs());
            if (result.getLastPrice().compareTo(result.getPrevPrice()) > 0) {
                result.setIncreased(true);
            } else {
                result.setIncreased(false);
            }
        }
        return repository.save(result);
    }

    @Override
    public BoxPlotData getBoxPlotData(String symbol, String from, String to) throws MyException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm");
        List<PairsModel> list;
        try {
            Date dateFrom = format.parse(from);
            Date dateTo = format.parse(to);
            list = repository.findAllByCurrencyAndTimestampBetween(
                    symbol,
                    dateFrom.getTime(),
                    dateTo.getTime()
            );
        } catch (ParseException e) {
            log.error("invalid dates");
            throw new MyException("invalid dates");
        }
        if (list.isEmpty()) {
            throw new MyException("Not found");
        }
        List<BigDecimal> prices = list.stream().map(PairsModel::getLastPrice).sorted().collect(Collectors.toList()); //TODO
        return calculationService.getBoxPlotDataByListOfValues(prices);
    }
}
