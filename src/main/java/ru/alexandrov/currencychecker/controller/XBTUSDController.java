package ru.alexandrov.currencychecker.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModel;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModelLite;
import ru.alexandrov.currencychecker.dao.repository.XBTUSDRepository;
import ru.alexandrov.currencychecker.service.XBTUSDService;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/xbtusd", produces = "application/json")
public class XBTUSDController {
    private final XBTUSDRepository repository;
    private final XBTUSDService service;

    @GetMapping(value = "/getLast")
    @ResponseBody
    List<XBTUSDModel> getLast(@RequestParam(value = "n", defaultValue = "1", required = false) int n) throws Exception {
        Page<XBTUSDModel> results = repository.findAll(new PageRequest(0, n, Sort.Direction.DESC, "id"));
        return results.getContent();
    }
    @GetMapping(value = "/getFiltered")
    @ResponseBody
    List<XBTUSDModel> getFilteredByDelta(@RequestParam(value = "delta", defaultValue = "1", required = false) int delta) throws Exception {
        return repository.findAllByDeltaAfter(delta);
    }
    @PostMapping(value = "/putToBase")
    ResponseEntity<XBTUSDModel> putToBase(@RequestBody XBTUSDModelLite lite){
        XBTUSDModel result = service.saveToDB(lite.getSymbol(), lite.getPrice());
        return ResponseEntity.ok(result);
    }
}
