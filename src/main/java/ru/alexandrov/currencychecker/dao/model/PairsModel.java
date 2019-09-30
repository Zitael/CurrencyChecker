package ru.alexandrov.currencychecker.dao.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Pairs")
@Data
public class PairsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    private Long id;

    @Basic
    private Long timestamp;
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
