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

    @GetMapping(value = "/lastNotes")
    @ResponseBody
    ResponseEntity<List> getLast(@RequestParam(value = "n", defaultValue = "1", required = false) int n) {
        return ResponseEntity.ok(service.getLastN(n));
    }

    @GetMapping(value = "/filteredNotes")
    @ResponseBody
    ResponseEntity<List> getFilteredByDelta(@RequestParam(value = "delta", defaultValue = "1", required = false) float delta) {
        return ResponseEntity.ok(service.getFilteredByDelta(delta));
    }

    @PostMapping(value = "/newNote")
    ResponseEntity<XBTUSDModel> putToBase(@RequestBody XBTUSDModelLite lite) {
        XBTUSDModel result = (XBTUSDModel) service.saveToDB(lite.getSymbol(), lite.getPrice());
        return ResponseEntity.ok(result);
    }
}
