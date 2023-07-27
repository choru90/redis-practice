package com.choru.stock.rank.dto;


import com.choru.stock.stock.entity.Stock;
import lombok.Getter;

@Getter
public class RankRes {

    private Long id;
    private String name;

    private Long code;

    private Double rate;

    private int price;

    private int index;

    private RankRes(Long id, String name, Long code, Double rate, int price, int index){
        this.id = id;
        this.name = name;
        this.code = code;
        this.rate = rate;
        this.price = price;
        this.index = index;
    }

    public static RankRes of(Stock stock, Double rate, int index){
        return new RankRes(stock.getId(), stock.getName(), stock.getCode(), rate, stock.getPrice(), index);
    }
}
