package ru.alexandrov.currencychecker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModel;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModelLite;
import ru.alexandrov.currencychecker.service.XBTUSDService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/xbtusd", produces = "application/json")
public class XBTUSDController {
    private final XBTUSDService service;

    @GetMapping(value = "/getLast")
    @ResponseBody
    List<XBTUSDModel> getLast(@RequestParam(value = "n", defaultValue = "1", required = false) int n) {
        return service.getLastN(n);
    }

    @GetMapping(value = "/getFiltered")
    @ResponseBody
    List<XBTUSDModel> getFilteredByDelta(@RequestParam(value = "delta", defaultValue = "1", required = false) float delta) {
        return service.getFilteredByDelta(delta);
    }

    @PostMapping(value = "/putToBase")
    ResponseEntity<XBTUSDModel> putToBase(@RequestBody XBTUSDModelLite lite) {
        XBTUSDModel result = service.saveToDB(lite.getSymbol(), lite.getPrice());
        return ResponseEntity.ok(result);
    }
}
