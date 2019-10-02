package ru.alexandrov.currencychecker.service.pairs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.repository.PairsRepository;
import ru.alexandrov.currencychecker.service.MyException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PairsServiceImplTest {
    @Mock
    private PairsRepository repository;
    @InjectMocks
    private PairsServiceImpl service;

    @Test
    public void getLastNFilteredByDelta() throws MyException {
        List<PairsModel> list = new ArrayList<>();
        list.add(new PairsModel());
        list.add(new PairsModel());

        when(repository.findAllByCurrencyAndDeltaAfter("1", 1, 2)).thenReturn(list);

        List result = service.getLastNFilteredByDelta("1", 2, 1);

        verify(repository, atLeastOnce()).findAllByCurrencyAndDeltaAfter("1", 1, 2);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void saveToDB() {
        PairsModel last = new PairsModel();
        last.setLastPrice(BigDecimal.ONE);

        when(repository.findFirstByCurrencyOrderByIdDesc("1")).thenReturn(last);
        when(repository.save(any(PairsModel.class))).then(returnsFirstArg());

        PairsModel model = service.saveToDB("1", BigDecimal.TEN);

        verify(repository, atLeastOnce()).findFirstByCurrencyOrderByIdDesc(any());
        assertNotNull(model);
        assertEquals(last.getLastPrice(), model.getPrevPrice());
    }
}