package ru.alexandrov.currencychecker.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.service.PairsService;
import ru.alexandrov.currencychecker.service.PairsServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PairsControllerTest {
    @Mock
    private PairsService service;
    @InjectMocks
    private PairsController controller;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

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
        when(service.getLastN("test1", 2)).thenReturn(list);
        when(service.getFilteredByDelta("test1", 1)).thenReturn(Collections.singletonList(model2));
    }

    @Test
    public void testInsertNote() throws Exception {
        //controller.putToBase(1, "test1");
        mockMvc.perform(get("/pairs/test1/newNote?price=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{status : 1}"))
                .andExpect(status().isOk());
//        verify(service, times(1)).saveToDB("test1", 1);
    }

    @Test
    public void getLast() throws Exception {
        mockMvc.perform(get("/pairs/test1/lastNotes?n=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//        verify(service).getLastN("test1", 2);
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