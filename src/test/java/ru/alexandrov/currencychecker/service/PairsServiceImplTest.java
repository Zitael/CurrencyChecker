package ru.alexandrov.currencychecker.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.repository.PairsRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PairsServiceImplTest {
    @Mock
    private PairsRepository repository;
    @Autowired
    private PairsService service;

    @Before
    public void setUp() throws Exception {
        PairsModel model1 = new PairsModel();
        model1.setLastPrice(1);
        PairsModel model2 = new PairsModel();
        model1.setLastPrice(2);
        PairsModel model3 = new PairsModel();
        model1.setLastPrice(3);
        List<PairsModel> list1 = new ArrayList<>();
        list1.add(model1);
        list1.add(model2);
        List<PairsModel> list2 = new ArrayList<>();
        list2.add(model3);

        when(repository.findAllByCurrency("1", new PageRequest(0, 1, Sort.Direction.DESC, "id")))
                .thenReturn((Page) list1);
        when(repository.findFirstByCurrencyOrderByIdDesc("2")).thenReturn(model1);
        when(repository.findAllByCurrencyAndDeltaAfter("1", 2)).thenReturn(list2);
    }

    @Test
    public void getLastN() {
        List result = service.getLastN("1", 1);
        assertEquals(2, result.size());
    }

    @Test
    public void getFilteredByDelta() {
    }

    @Test
    public void saveToDB() {

    }
}