package ru.alexandrov.currencychecker.dao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class PairsModelLite {
    @JsonProperty(value = "symbol")
    private String symbol;
    @JsonProperty(value = "timestamp")
    private String timestamp;
    @JsonProperty(value = "midPrice")
    private BigDecimal price;
}
