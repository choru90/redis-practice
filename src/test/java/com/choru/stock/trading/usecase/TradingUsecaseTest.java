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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradingUsecaseTest {

    private TradingUsecase usecase;
    @Mock
    RedisComponent redisComponent;
    @Mock
    StockService stockService;
    @Mock
    TradingService tradingService;



    @Test
    void addTradingRecordLatestIsEmptyTest() throws NoSuchFieldException, IllegalAccessException {

        // given
        usecase = new TradingUsecase(redisComponent, stockService, tradingService);
        Long code = 300L;
        int volume= 500;
        int price = 90000;
        Long id = 1L;
        Double rate = Double.valueOf((price - price) / price);
        String latestPriceKey = null;


        Stock stock = new Stock();
        Field idField = stock.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(stock, id);
        Field codeField = stock.getClass().getDeclaredField("code");
        codeField.setAccessible(true);
        codeField.set(stock, code);
        Field priceField = stock.getClass().getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(stock, price);

        LocalDate tradingDate = LocalDate.now();

        when(stockService.getStock(code)).thenReturn(stock);
        when(tradingService.saveTradingRecord(stock, volume, price,tradingDate)).thenReturn(TradingRecord.of(volume, price, tradingDate, stock));
        when(redisComponent.score(latestPriceKey, code.toString())).thenReturn(null, Double.valueOf(price));
        when(tradingService.getYesterdayPrice(tradingDate, stock)).thenReturn(null);
        when(tradingService.saveYestedayPrice(stock, tradingDate, price)).thenReturn(YesterdayPrice.of(stock, tradingDate, price));
        when(redisComponent.add(latestPriceKey, code.toString(), price)).thenReturn(null);
        doNothing().when(redisComponent).expireOneDay(latestPriceKey);
        doNothing().when(stockService).updatePrice(id, price);
        when(tradingService.saveTradingVolume(tradingDate, stock, volume)).thenReturn(TradingVolume.of(tradingDate, stock, volume));
        when(redisComponent.incrementScore(RankTagType.TRADING_VOLUME.getKey(), stock.getCode().toString(), volume)).thenReturn(null);
        doNothing().when(redisComponent).expireOneDay(RankTagType.TRADING_VOLUME.getKey());
        when(tradingService.savePriceChangeRate(stock, tradingDate, rate)).thenReturn(TradingPriceChangeRate.of(rate, tradingDate, stock));
        when(redisComponent.add(RankTagType.TRADING_PRICE_RATE.getKey(), code.toString(),rate)).thenReturn(null);
        doNothing().when(redisComponent).expireOneDay(RankTagType.TRADING_PRICE_RATE.getKey());

        // when
        usecase.addTradingRecord(code, volume, price);
        // then

        verify(tradingService, times(1)).saveYestedayPrice(stock, tradingDate,price);
        verify(redisComponent, times(1)).add(latestPriceKey, code.toString(),price);
        verify(redisComponent, times(1)).expireOneDay(latestPriceKey);
        verify(stockService, times(1)).updatePrice(id, price);
        verify(tradingService, times(1)).saveTradingVolume(tradingDate, stock, volume);
        verify(redisComponent, times(1)).incrementScore(RankTagType.TRADING_VOLUME.getKey(), stock.getCode().toString(), volume);
        verify(redisComponent, times(1)).expireOneDay(RankTagType.TRADING_VOLUME.getKey());
        verify(tradingService, times(1)).savePriceChangeRate(stock, tradingDate, rate);
        verify(redisComponent, times(1)).add(RankTagType.TRADING_PRICE_RATE.getKey(), code.toString(), rate);
        verify(redisComponent, times(1)).expireOneDay(RankTagType.TRADING_PRICE_RATE.getKey());

    }

    @Test
    void addTradingRecordLatestIsNotEmptyTest() throws NoSuchFieldException, IllegalAccessException {

        // given
        usecase = new TradingUsecase(redisComponent, stockService, tradingService);
        Long code = 300L;
        int volume= 500;
        int price = 90000;
        Long id = 1L;
        Double rate = Double.valueOf((price - price) / price);
        String latestPriceKey = null;


        Stock stock = new Stock();
        Field idField = stock.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(stock, id);
        Field codeField = stock.getClass().getDeclaredField("code");
        codeField.setAccessible(true);
        codeField.set(stock, code);
        Field priceField = stock.getClass().getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(stock, price);

        LocalDate tradingDate = LocalDate.now();

        when(stockService.getStock(code)).thenReturn(stock);
        when(tradingService.saveTradingRecord(stock, volume, price,tradingDate)).thenReturn(TradingRecord.of(volume, price, tradingDate, stock));
        when(redisComponent.score(latestPriceKey, code.toString())).thenReturn(Double.valueOf(price));
        when(tradingService.getYesterdayPrice(tradingDate, stock)).thenReturn(null);

        doNothing().when(stockService).updatePrice(id, price);
        when(tradingService.saveTradingVolume(tradingDate, stock, volume)).thenReturn(TradingVolume.of(tradingDate, stock, volume));
        when(redisComponent.incrementScore(RankTagType.TRADING_VOLUME.getKey(), stock.getCode().toString(), volume)).thenReturn(null);
        doNothing().when(redisComponent).expireOneDay(RankTagType.TRADING_VOLUME.getKey());
        when(tradingService.savePriceChangeRate(stock, tradingDate, rate)).thenReturn(TradingPriceChangeRate.of(rate, tradingDate, stock));
        when(redisComponent.add(RankTagType.TRADING_PRICE_RATE.getKey(), code.toString(),rate)).thenReturn(null);
        doNothing().when(redisComponent).expireOneDay(RankTagType.TRADING_PRICE_RATE.getKey());

        // when
        usecase.addTradingRecord(code, volume, price);
        // then

        verify(tradingService, times(0)).saveYestedayPrice(stock, tradingDate,price);
        verify(redisComponent, times(0)).add(latestPriceKey, code.toString(),price);
        verify(redisComponent, times(0)).expireOneDay(latestPriceKey);
        verify(stockService, times(1)).updatePrice(id, price);
        verify(tradingService, times(1)).saveTradingVolume(tradingDate, stock, volume);
        verify(redisComponent, times(1)).incrementScore(RankTagType.TRADING_VOLUME.getKey(), stock.getCode().toString(), volume);
        verify(redisComponent, times(1)).expireOneDay(RankTagType.TRADING_VOLUME.getKey());
        verify(tradingService, times(1)).savePriceChangeRate(stock, tradingDate, rate);
        verify(redisComponent, times(1)).add(RankTagType.TRADING_PRICE_RATE.getKey(), code.toString(), rate);
        verify(redisComponent, times(1)).expireOneDay(RankTagType.TRADING_PRICE_RATE.getKey());

    }

}