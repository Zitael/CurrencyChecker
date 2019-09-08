package ru.alexandrov.currencychecker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModel;
import ru.alexandrov.currencychecker.dao.repository.XBTUSDRepository;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/xbtusd", produces = "application/json")
public class XBTUSDController {
    private final XBTUSDRepository repository;

    @GetMapping(value = "/getLast")
    @ResponseBody
    List<XBTUSDModel> getLast(@RequestParam(value = "n", defaultValue = "1", required = false) int n) throws Exception {
        List<XBTUSDModel> results = repository.findAll();
        return results;
    }
    @GetMapping(value = "/getFiltered")
    @ResponseBody
    List<XBTUSDModel> getFilteredByDelta(@RequestParam(value = "delta", defaultValue = "1", required = false) int n) throws Exception {
        List<XBTUSDModel> results = repository.findAll();
        return results;
    }
}
