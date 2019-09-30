package ru.alexandrov.currencychecker.dao.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BoxPlotData {
    private float median25;
    private float median50;
    private float median75;
    private float min;
    private float max;
    private String message;
}
