package com.choru.stock.trading.entity;

import com.choru.stock.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int volume;

    @Column
    private int price;

    @Column
    private LocalDate tradingDate;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private TradingRecord(int volume, int price, LocalDate tradingDate, Stock stock){
        this.volume = volume;
        this.price = price;
        this.tradingDate = tradingDate;
        this.stock = stock;
    }

    public static TradingRecord of(int volume, int price, LocalDate tradingDate, Stock stock){
        return new TradingRecord(volume, price, tradingDate, stock);
    }

}
