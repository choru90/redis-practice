package com.choru.stock.trading.entity;

import com.choru.stock.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockTradingDateEmbId implements Serializable {

    @Column
    private LocalDate tradingDate;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private StockTradingDateEmbId(LocalDate tradingDate, Stock stock){
        this.tradingDate = tradingDate;
        this.stock = stock;
    }

    public static StockTradingDateEmbId of(LocalDate tradingDate, Stock stock){
        return  new StockTradingDateEmbId(tradingDate, stock);
    }

}
