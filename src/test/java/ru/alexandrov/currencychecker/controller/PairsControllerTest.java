package ru.alexandrov.currencychecker.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.service.PairsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PairsControllerTest {
    @Mock
    private PairsService service;
    @InjectMocks
    private PairsController controller;

    @Before
    public void setUp() {
        PairsModel model1 = new PairsModel();
        model1.setCurrency("test1");
        model1.setLastPrice(1);
        PairsModel model2 = new PairsModel();
        model2.setCurrency("test1");
        model2.setLastPrice(2);
        List<PairsModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        when(service.saveToDB("test1", 1)).thenReturn(model1);
        when(service.getLastNFilteredByDelta("test1", 2, 2)).thenReturn(list);
    }

    @Test
    public void getLast() {
        List<PairsModel> list = new ArrayList<>();
        list.add(new PairsModel());
        list.add(new PairsModel());

        when(service.getLastNFilteredByDelta(any(String.class), anyInt(), anyFloat())).thenReturn(list);

        List result = controller.getLast(1, 1,"test").getBody();

        verify(service).getLastNFilteredByDelta("test", 1, 1);
        assertNotNull(result);
        assertEquals(result.size(), 2);
    }


    @Test
    public void putToBase() {
        PairsModel last = new PairsModel();
        last.setLastPrice(1);

        when(service.saveToDB(any(String.class), anyFloat())).thenReturn(last);

        PairsModel model = controller.putToBase(2, "1").getBody();

        verify(service, atLeastOnce()).saveToDB("1", 2);
        assertNotNull(model);
    }
}