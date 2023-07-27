package com.choru.stock.trading.service;

import com.choru.stock.stock.entity.Stock;
import com.choru.stock.trading.entity.*;
import com.choru.stock.trading.repository.PriceChangeRateRepository;
import com.choru.stock.trading.repository.TradingRecordRepository;
import com.choru.stock.trading.repository.TradingVolumeRepository;
import com.choru.stock.trading.repository.YesterdayPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TradingService {
    private final TradingRecordRepository repository;
    private final PriceChangeRateRepository priceChangeRateRepository;

    private final TradingVolumeRepository tradingVolumeRepository;

    private final YesterdayPriceRepository yesterdayPriceRepository;


    @Transactional
    public TradingRecord saveTradingRecord(Stock stock, int count, int price, LocalDate date){
        return repository.save(TradingRecord.of(count, price, date, stock));
    }

    @Transactional
    public TradingPriceChangeRate savePriceChangeRate(Stock stock, LocalDate tradingDate, Double rate){
        return priceChangeRateRepository.save(TradingPriceChangeRate.of(rate,tradingDate, stock));
    }

    @Transactional
    public TradingVolume saveTradingVolume(LocalDate tradingDate, Stock stock, int volume){
        return tradingVolumeRepository.save(TradingVolume.of(tradingDate, stock, volume));
    }

    public YesterdayPrice saveYestedayPrice(Stock stock, LocalDate tradingDate, int price){
        return yesterdayPriceRepository.save(YesterdayPrice.of(stock, tradingDate, price));
    }

    public YesterdayPrice getYesterdayPrice(LocalDate tradingDate, Stock stock){
        return yesterdayPriceRepository.findByEmbId(StockTradingDateEmbId.of(tradingDate, stock));
    }
}
