package com.choru.stock.trading.entity;

import com.choru.stock.stock.entity.Stock;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Embeddable
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YesterdayPrice {

    @EmbeddedId
    private StockTradingDateEmbId embId;

    private int price;

    private YesterdayPrice(StockTradingDateEmbId embId, int price){
        this.embId = embId;
        this.price = price;
    }

    public static YesterdayPrice of(Stock stock, LocalDate tradingDate, int price){
        return new YesterdayPrice(StockTradingDateEmbId.of(tradingDate, stock), price) ;
    }
}
