package ru.alexandrov.currencychecker.dao.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BoxPlotData {
    private BigDecimal median25;
    private BigDecimal median50;
    private BigDecimal median75;
    private BigDecimal min;
    private BigDecimal max;
    private String message;
}
