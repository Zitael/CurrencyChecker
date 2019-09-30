package ru.alexandrov.currencychecker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.BoxPlotData;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.repository.PairsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PairsServiceImpl implements PairsService<PairsModel, BoxPlotData> {
    private final PairsRepository repository;

    @Override
    public List<PairsModel> getLastNFilteredByDelta(String symbol, int count, float delta) {
        List<PairsModel> filteredList = repository.findAllByCurrencyAndDeltaAfter(symbol.toUpperCase(), delta);
        return filteredList.stream().sorted(Collections.reverseOrder()).limit(count).collect(Collectors.toList());
    }

    @Override
    public PairsModel saveToDB(String symbol, float price) {
        PairsModel result = new PairsModel();
        PairsModel last = repository.findFirstByCurrencyOrderByIdDesc(symbol.toUpperCase());
        result.setTimestamp(System.currentTimeMillis());
        result.setCurrency(symbol.toUpperCase());
        result.setPrevPrice(last == null ? 0 : last.getLastPrice());
        result.setLastPrice(price);
        result.setDelta(Math.abs((int) (result.getLastPrice() - result.getPrevPrice())));
        if (result.getLastPrice() > result.getPrevPrice()) result.setIncreased(1);
        else result.setIncreased(0);
        return repository.save(result);
    }

    @Override
    public BoxPlotData getBoxPlotData(String symbol, String from, String to) {
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
            return new BoxPlotData().setMessage("invalid dates");
        }
        if (list.isEmpty()) return new BoxPlotData().setMessage("Not found");
        List<Float> prices = list.stream().map(PairsModel::getLastPrice).sorted().collect(Collectors.toList());
        int minIndex = 0;
        int maxIndex = prices.size() - 1;
        int median25index;
        int median50index1;
        int median50index2;
        int median75index;

        BoxPlotData result = new BoxPlotData();
        // Если количество данных четное
        if (prices.size() % 2 == 0) {
            // Берем два средних индекса для 50 медиан
            median50index1 = prices.size() / 2 - 1;
            median50index2 = prices.size() / 2;
        } else {
            // берем средний индекс для 50 медианы
            median50index1 = (prices.size() - 1) / 2;
            median50index2 = (prices.size() - 1) / 2;
        }
        // Если индекс меньшей 50 медианы четный, значит слева четное количество, считаем вместе с медианой и берем средний индекс
        // Если же он нечетный, значит слева нечетное число, берем средний индекс
        median25index = median50index1 % 2 == 0
                ? median50index1 / 2
                : (median50index1 - 1) / 2;
        // Если справа от медианы четное количество, считаем с медианой и берем средний индекс
        // Если же нечетное, считаем без медианы и берем средний индекс
        median75index = (maxIndex - median50index2) % 2 == 0
                ? median50index2 + (maxIndex - median50index2) / 2
                : median50index2 + (maxIndex - median50index2 + 1) / 2;

        result.setMax(prices.get(maxIndex));
        result.setMin(prices.get(minIndex));
        result.setMedian25(prices.get(median25index));
        result.setMedian50((prices.get(median50index1) + prices.get(median50index2)) / 2);
        result.setMedian75(prices.get(median75index));
        return result;
    }
}
