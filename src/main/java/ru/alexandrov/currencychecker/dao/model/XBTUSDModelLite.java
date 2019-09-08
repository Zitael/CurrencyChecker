package ru.alexandrov.currencychecker.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class XBTUSDModelLite {
    @JsonProperty(value = "symbol")
    private String symbol;
    @JsonIgnore
    @JsonProperty(value = "timestamp")
    private String timestamp;
    @JsonProperty(value = "midPrice")
    private Float price;
}
