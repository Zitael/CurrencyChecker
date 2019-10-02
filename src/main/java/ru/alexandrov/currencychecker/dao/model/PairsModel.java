package ru.alexandrov.currencychecker.dao.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Pairs")
@Data
@Accessors(chain = true)
public class PairsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    private Long id;

    @Basic
    private Date timestamp;
    @Basic
    private String currency;
    @Column(name = "prev_price")
    private BigDecimal prevPrice;
    @Basic
    private BigDecimal delta;
    @Column(name = "last_price")
    private BigDecimal lastPrice;
    @Basic
    private boolean increased;
}
