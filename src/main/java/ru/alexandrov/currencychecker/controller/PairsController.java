package ru.alexandrov.currencychecker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.service.PairsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pairs/{symbol}", produces = "application/json")
public class PairsController {
    private final PairsService service;

    @GetMapping(value = "/lastNotes")
    @ResponseBody
    ResponseEntity<List> getLast(@RequestParam(value = "n", defaultValue = "1", required = false) int n,
                                 @PathVariable(value = "symbol") String symbol) {
        return ResponseEntity.ok(service.getLastN(symbol, n));
    }

    @GetMapping(value = "/filteredNotes")
    @ResponseBody
    ResponseEntity<List> getFilteredByDelta(@RequestParam(value = "delta", defaultValue = "1", required = false) float delta,
                                            @PathVariable(value = "symbol") String symbol) {
        return ResponseEntity.ok(service.getFilteredByDelta(symbol, delta));
    }

    @GetMapping(value = "/newNote")
    ResponseEntity<PairsModel> putToBase(@RequestParam(value = "price") float price,
                                         @PathVariable(value = "symbol") String symbol) {
        return ResponseEntity.ok((PairsModel) service.saveToDB(symbol, price));
    }
}
