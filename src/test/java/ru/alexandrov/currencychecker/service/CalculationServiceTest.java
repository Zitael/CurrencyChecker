package ru.alexandrov.currencychecker.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alexandrov.currencychecker.dao.model.BoxPlotData;
import ru.alexandrov.currencychecker.service.calculation.CalculationService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CalculationServiceTest {
    @InjectMocks
    private CalculationService calculationService;

    @Test
    public void getBoxPlotDataByListOfValuesOdd() throws MyException {
        List<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal(1));
        values.add(new BigDecimal(2));
        values.add(new BigDecimal(3));
        values.add(new BigDecimal(4));
        values.add(new BigDecimal(5));
        values.add(new BigDecimal(6));
        values.add(new BigDecimal(7));
        values.add(new BigDecimal(8));
        values.add(new BigDecimal(9));
        values.add(new BigDecimal(10));

        BoxPlotData result = calculationService.getBoxPlotDataByListOfValues(values);

        assertNotNull(result);
        assertEquals(new BigDecimal(5.50).setScale(2), result.getMedian50());
        assertEquals(new BigDecimal(3), result.getMedian25());
        assertEquals(new BigDecimal(8), result.getMedian75());
        assertEquals(new BigDecimal(1), result.getMin());
        assertEquals(new BigDecimal(10), result.getMax());
    }

    @Test
    public void getBoxPlotDataByListOfValuesEven() throws MyException {
        List<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal(1));
        values.add(new BigDecimal(2));
        values.add(new BigDecimal(3));
        values.add(new BigDecimal(4));
        values.add(new BigDecimal(5));
        values.add(new BigDecimal(6));
        values.add(new BigDecimal(7));
        values.add(new BigDecimal(8));
        values.add(new BigDecimal(9));

        BoxPlotData result = calculationService.getBoxPlotDataByListOfValues(values);

        assertNotNull(result);
        assertEquals(new BigDecimal(5), result.getMedian50());
        assertEquals(new BigDecimal(3), result.getMedian25());
        assertEquals(new BigDecimal(7), result.getMedian75());
        assertEquals(new BigDecimal(1), result.getMin());
        assertEquals(new BigDecimal(9), result.getMax());
    }
}