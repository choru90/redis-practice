package com.choru.stock.trading.entity;

import com.choru.stock.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradingPriceChangeRate {
    @EmbeddedId
    private StockTradingDateEmbId empId;

    @Column(columnDefinition = "double default 0.0")
    private double rate;

    private TradingPriceChangeRate(double rate, LocalDate tradingDate, Stock stock){
        this.empId = StockTradingDateEmbId.of(tradingDate, stock);
        this.rate = rate;
    }

    public static TradingPriceChangeRate of(double rate, LocalDate tradingDate, Stock stock){
        return new TradingPriceChangeRate(rate, tradingDate, stock);
    }
}
