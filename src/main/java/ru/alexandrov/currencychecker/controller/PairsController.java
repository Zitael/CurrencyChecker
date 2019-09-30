package ru.alexandrov.currencychecker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.currencychecker.dao.model.BoxPlotData;
import ru.alexandrov.currencychecker.dao.model.PairsModel;
import ru.alexandrov.currencychecker.dao.model.PairsModelLite;
import ru.alexandrov.currencychecker.service.PairsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pairs/{symbol}", produces = "application/json")
public class PairsController {
    private final PairsService service;

    @GetMapping(value = "/notes")
    @ResponseBody
    ResponseEntity<List> getLast(
            @RequestParam(value = "n", defaultValue = "1", required = false) int n,
            @RequestParam(value = "delta", defaultValue = "0", required = false) float delta,
            @PathVariable(value = "symbol") String symbol
    ) {
        return ResponseEntity.ok(service.getLastNFilteredByDelta(symbol, n, delta));
    }

    @PostMapping(value = "/notes")
    ResponseEntity<PairsModel> putToBase(
            @RequestBody PairsModelLite modelLite,
            @PathVariable(value = "symbol") String symbol
    ) {
        return ResponseEntity.ok((PairsModel) service.saveToDB(symbol, modelLite.getPrice()));
    }

    @GetMapping(value = "/boxplot")
    ResponseEntity<BoxPlotData> getBoxPlotData(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @PathVariable(value = "symbol") String symbol
    ) {
        return ResponseEntity.ok((BoxPlotData) service.getBoxPlotData(symbol, from, to));
    }
}
