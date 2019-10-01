package ru.alexandrov.currencychecker.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.repository.PairsRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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

    @Before
    public void setUp() {
    }

    @Test
    public void getLastN() {
        List<PairsModel> list = new ArrayList<>();
        list.add(new PairsModel());
        list.add(new PairsModel());
        Page<PairsModel> page = new PageImpl<>(list);

        when(repository.findAllByCurrency(any(String.class), any(PageRequest.class))).thenReturn(page);

        List result = service.getLastNFilteredByDelta("1", 2, 1);

        verify(repository, atLeastOnce()).findAllByCurrency(any(), any());
        assertEquals(2, result.size());
    }

    @Test
    public void getFilteredByDelta() {
        List<PairsModel> list = new ArrayList<>();
        list.add(new PairsModel());
        list.add(new PairsModel());

        when(repository.findAllByCurrencyAndDeltaAfter(any(), anyFloat(), anyInt())).thenReturn(list);

        List result = service.getLastNFilteredByDelta("1", 1, 1);

        verify(repository, atLeastOnce()).findAllByCurrencyAndDeltaAfter(any(), anyFloat(), anyInt());
        assertEquals(2, result.size());
    }

    @Test
    public void saveToDB() {
        PairsModel last = new PairsModel();
        last.setLastPrice(1);

        when(repository.findFirstByCurrencyOrderByIdDesc("1")).thenReturn(last);
        when(repository.save(any(PairsModel.class))).then(returnsFirstArg());

        PairsModel model = service.saveToDB("1", 2);

        verify(repository, atLeastOnce()).findFirstByCurrencyOrderByIdDesc(any());
        assertNotNull(model);
        assertEquals(last.getLastPrice(), model.getPrevPrice(), 0.0);
    }
}