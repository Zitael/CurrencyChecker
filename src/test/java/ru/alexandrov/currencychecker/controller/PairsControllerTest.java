package ru.alexandrov.currencychecker.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alexandrov.currencychecker.dao.model.BoxPlotData;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.model.PairsModelLite;
import ru.alexandrov.currencychecker.service.MyException;
import ru.alexandrov.currencychecker.service.pairs.PairsService;

import java.math.BigDecimal;
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

    @Test
    public void getLastWhenSuccess() throws MyException {
        List<PairsModel> list = new ArrayList<>();
        list.add(new PairsModel());
        list.add(new PairsModel());

        when(service.getLastNFilteredByDelta(anyString(), anyInt(), anyFloat())).thenReturn(list);

        ResponseEntity result = controller.getLast("test", 1, 1);
        List resultBody = (List) result.getBody();

        verify(service).getLastNFilteredByDelta("test", 1, 1);
        assertNotNull(resultBody);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resultBody.size(), 2);
    }

    @Test
    public void getLastWhenException() throws MyException {
        when(service.getLastNFilteredByDelta(anyString(), anyInt(), anyFloat())).thenThrow(new MyException("TestException"));

        ResponseEntity result = controller.getLast("test", 1, 1);
        String resultBody = (String) result.getBody();

        verify(service).getLastNFilteredByDelta("test", 1, 1);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("TestException", resultBody);
    }

    @Test
    public void saveToBase() throws MyException {
        PairsModel last = new PairsModel();
        last.setLastPrice(BigDecimal.ONE);

        when(service.saveToDB(any(), any(), any())).thenReturn(last);

        ResponseEntity result = controller.saveToBase("1", new PairsModelLite().setPrice(BigDecimal.TEN));
        PairsModel model = (PairsModel) result.getBody();

        verify(service, atLeastOnce()).saveToDB(any(), any(), any());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(model);
        assertEquals(last, model);
    }

    @Test
    public void getBoxPlotDataWhenSuccess() throws MyException {
        BoxPlotData boxPlotData = new BoxPlotData();

        when(service.getBoxPlotData(any(), any(), any())).thenReturn(boxPlotData);

        ResponseEntity result = controller.getBoxPlotData("1", "2", "3");
        BoxPlotData model = (BoxPlotData) result.getBody();

        verify(service, atLeastOnce()).getBoxPlotData("1", "2", "3");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(model);
        assertEquals(boxPlotData, model);
    }

    @Test
    public void getBoxPlotDataWhenException() throws MyException {
        when(service.getBoxPlotData(any(), any(), any())).thenThrow(new MyException("TestException"));

        ResponseEntity result = controller.getBoxPlotData("1", "2", "3");
        String model = (String) result.getBody();

        verify(service, atLeastOnce()).getBoxPlotData("1", "2", "3");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(model);
        assertEquals("TestException", model);
    }
}