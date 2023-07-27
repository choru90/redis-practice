package com.choru.stock.trading.entity;

import com.choru.stock.stock.entity.Stock;
import jakarta.persistence.Column;
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
public class TradingVolume {

    @EmbeddedId
    private StockTradingDateEmbId empId;

    @Column
    private int volume;

    private TradingVolume(LocalDate tradingDate, Stock stock, int volume){
        this.empId = StockTradingDateEmbId.of(tradingDate, stock);
        this.volume = volume;
    }

    public static TradingVolume of(LocalDate tradingDate, Stock stock, int volume){
        return new TradingVolume(tradingDate, stock, volume);
    }
}
