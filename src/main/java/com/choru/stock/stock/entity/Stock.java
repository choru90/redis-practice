package com.choru.stock.stock.entity;

import com.choru.stock.trading.entity.TradingRecord;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long code;

    @Column
    private String name;

    @Column(columnDefinition = "integer default 0")
    private int price;

    public void updatePrice(int price){
        this.price = price;
    }


}
