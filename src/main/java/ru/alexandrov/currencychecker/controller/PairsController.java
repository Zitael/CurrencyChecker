package ru.alexandrov.currencychecker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.currencychecker.dao.model.BoxPlotData;
import ru.alexandrov.currencychecker.dao.model.ErrorResponse;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.model.PairsModelLite;
import ru.alexandrov.currencychecker.service.MyException;
import ru.alexandrov.currencychecker.service.pairs.PairsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pairs", produces = "application/json")
public class PairsController {
    private final PairsService service;

    @GetMapping(value = "/{symbol}")
    @ResponseBody
    ResponseEntity getLast(
            @PathVariable(value = "symbol") String symbol,
            @RequestParam(value = "n", defaultValue = "1", required = false) int n,
            @RequestParam(value = "delta", defaultValue = "0", required = false) float delta
    ) {
        try {
            return ResponseEntity.ok(service.getLastNFilteredByDelta(symbol, n, delta));
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping(value = "/{symbol}")
    ResponseEntity saveToBase(
            @PathVariable(value = "symbol") String symbol,
            @RequestBody PairsModelLite modelLite
    ) {
        try {
            return ResponseEntity.ok((PairsModel) service.saveToDB(symbol, modelLite.getPrice(), modelLite.getTimestamp()));
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping(value = "/{symbol}/boxplot")
    ResponseEntity getBoxPlotData(
            @PathVariable(value = "symbol") String symbol,
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to
    ) {
        try {
            return ResponseEntity.ok((BoxPlotData) service.getBoxPlotData(symbol, from, to));
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
