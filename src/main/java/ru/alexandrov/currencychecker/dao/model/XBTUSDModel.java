package ru.alexandrov.currencychecker.dao.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "BTCUSD")
@Data
public class XBTUSDModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "timestamp")
    private String timestamp;
    @Column(name = "currency")
    private String currency;
    @Column(name = "prev_price")
    private float prevPrice;
    @Column(name = "delta")
    private int delta;
    @Column(name = "last_price")
    private float lastPrice;
    @Column(name = "increased")
    private int increased;
}
