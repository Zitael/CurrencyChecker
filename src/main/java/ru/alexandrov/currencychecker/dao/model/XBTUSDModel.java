package ru.alexandrov.currencychecker.dao.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "BTCUSD")
@Data
public class XBTUSDModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private Long id;

    @Basic
    private String timestamp;
    @Basic
    private String currency;
    @Column(name = "prev_price")
    private float prevPrice;
    @Basic
    private float delta;
    @Column(name = "last_price")
    private float lastPrice;
    @Basic
    private int increased;
}
