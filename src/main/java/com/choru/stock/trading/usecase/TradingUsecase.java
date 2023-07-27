package com.choru.stock.trading.usecase;

import com.choru.stock.common.code.RankTagType;
import com.choru.stock.common.component.RedisComponent;
import com.choru.stock.stock.entity.Stock;
import com.choru.stock.stock.service.StockService;
import com.choru.stock.trading.entity.TradingPriceChangeRate;
import com.choru.stock.trading.entity.TradingRecord;
import com.choru.stock.trading.entity.TradingVolume;
import com.choru.stock.trading.entity.YesterdayPrice;
import com.choru.stock.trading.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TradingUsecase {

    private final RedisComponent redisComponent;
    private final StockService stockService;

    private final TradingService service;

    @Value("${key.latest.price}")
    private String latestPriceKey;

    @Transactional
    public void addTradingRecord(Long code, int volume, int price){
        LocalDate tradingDate = LocalDate.now();
        Stock stock = stockService.getStock(code);

        TradingRecord savedTradingRecord = service.saveTradingRecord(stock, volume, price, tradingDate);
        Stock savedStock = savedTradingRecord.getStock();
        saveLatestPrice(savedStock.getCode(), savedStock.getPrice(), tradingDate, savedStock);

        stockService.updatePrice(stock.getId(), price);

        LocalDate savedTradingDate = savedTradingRecord.getTradingDate();
        saveTradingVolume(savedTradingRecord.getVolume(), savedTradingDate, savedStock);
        saveRate(price, savedTradingDate, savedStock);
    }

    private void saveTradingVolume(int volume, LocalDate tradingDate, Stock stock) {
        TradingVolume savedVolume = service.saveTradingVolume(tradingDate, stock, volume);

        redisComponent.incrementScore(RankTagType.TRADING_VOLUME.getKey(), savedVolume.getEmpId().getStock().getCode().toString(), savedVolume.getVolume());
        redisComponent.expireOneDay(RankTagType.TRADING_VOLUME.getKey());
    }

    private void saveRate(int price, LocalDate tradingDate, Stock stock) {
        Long code = stock.getCode();
        Double yesterdayPrice = redisComponent.score(latestPriceKey, code.toString());

        TradingPriceChangeRate tradingPriceChangeRate = service.savePriceChangeRate(stock, tradingDate, getRate(Double.valueOf(yesterdayPrice), Double.valueOf(price)));
        redisComponent.add(RankTagType.TRADING_PRICE_RATE.getKey(), code.toString(), tradingPriceChangeRate.getRate());
        redisComponent.expireOneDay(RankTagType.TRADING_PRICE_RATE.getKey());
    }

    private void saveLatestPrice(Long code, int price, LocalDate tradingDate, Stock stock) {
        Double score = redisComponent.score(latestPriceKey, code.toString());
        YesterdayPrice savedYesterdayPrice = service.getYesterdayPrice(tradingDate, stock);

        if (ObjectUtils.isEmpty(savedYesterdayPrice) && ObjectUtils.isEmpty(score)) {
            savedYesterdayPrice = service.saveYestedayPrice(stock, tradingDate, price);
            redisComponent.add(latestPriceKey, code.toString(), savedYesterdayPrice.getPrice());
            redisComponent.expireOneDay(latestPriceKey);
        }
    }
    private Double getRate(Double yesterdayPrice, Double price){
        return (price - yesterdayPrice) / yesterdayPrice;
    }

}
