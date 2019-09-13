package ru.alexandrov.currencychecker.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.service.PairsService;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class PairsControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PairsService service;

    @InjectMocks
    private PairsController controller;

    @Before
    public void setUp() throws Exception {
        PairsModel model1 = new PairsModel();
        model1.setCurrency("test1");
        model1.setLastPrice(2);
        when(service.saveToDB("test1", 2)).thenReturn(model1);
    }

    @Test
    public void getLast() throws Exception {
        mockMvc.perform(get("/test1/lastNotes?n=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFilteredByDelta() {
    }

    @Test
    public void putToBase() throws Exception {
        mockMvc.perform(get("/test1/newNote?price=2"))
                .andExpect(status().isOk());
    }
    }