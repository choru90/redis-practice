package com.choru.stock.rank.dto;


import com.choru.stock.stock.entity.Stock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RankRes {

    @Schema(description = "ID")
    private Long id;

    @Schema(description ="주식명")
    private String name;

    @Schema(description = "주식 코드")
    private Long code;

    @Schema(description = "변동률")
    private Double rate;

    @Schema(description = "가격")
    private int price;

    @Schema(description = "순서")
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
