package ru.alexandrov.currencychecker.service.calculation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alexandrov.currencychecker.dao.model.BoxPlotData;
import ru.alexandrov.currencychecker.service.MyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
public class CalculationService {
    public BoxPlotData getBoxPlotDataByListOfValues(List<BigDecimal> prices) throws MyException {
        if (prices == null || prices.isEmpty()){
            throw new MyException("No data for BoxPlot");
        }
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
        result.setMedian50(
                (prices.get(median50index1).add(prices.get(median50index2)))
                        .divide(new BigDecimal(2), 2, RoundingMode.HALF_UP));
        result.setMedian75(prices.get(median75index));
        return result;
    }
}
