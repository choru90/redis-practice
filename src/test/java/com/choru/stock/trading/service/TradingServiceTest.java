package com.choru.stock.trading.service;

import com.choru.stock.stock.entity.Stock;
import com.choru.stock.trading.entity.*;
import com.choru.stock.trading.repository.PriceChangeRateRepository;
import com.choru.stock.trading.repository.TradingRecordRepository;
import com.choru.stock.trading.repository.TradingVolumeRepository;
import com.choru.stock.trading.repository.YesterdayPriceRepository;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradingServiceTest {

    @Mock
    private TradingRecordRepository repository;
    @Mock
    private PriceChangeRateRepository priceChangeRateRepository;
    @Mock
    private TradingVolumeRepository tradingVolumeRepository;
    @Mock
    private YesterdayPriceRepository yesterdayPriceRepository;
    private TradingService service;


    @Test
    void saveTradingRecordTest() throws IllegalAccessException, NoSuchFieldException {
        // give
        service = new TradingService(repository, priceChangeRateRepository, tradingVolumeRepository, yesterdayPriceRepository);
        Long id = 1L;
        int count = 200;
        int price = 5000;
        LocalDate nowDate = LocalDate.now();

        Stock stock = getStock(id);
        TradingRecord tradingRecord = TradingRecord.of(count, price, nowDate, stock);
        when(repository.save(any(TradingRecord.class))).thenReturn(tradingRecord);

        // when
        TradingRecord resultTradingRecord = service.saveTradingRecord(stock, count, price, nowDate);
        // then

        assertEquals(id, resultTradingRecord.getStock().getId());
        assertEquals(count, resultTradingRecord.getVolume());
        assertEquals(price, resultTradingRecord.getPrice());
        assertEquals(nowDate, resultTradingRecord.getTradingDate());
    }

    private Stock getStock(Long id) throws NoSuchFieldException, IllegalAccessException {
        Stock stock = new Stock();
        Field idField = stock.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(stock, id);
        return stock;
    }

    @Test
    void savePriceChangeRateTest() throws NoSuchFieldException, IllegalAccessException {
        // give
        service = new TradingService(repository, priceChangeRateRepository, tradingVolumeRepository, yesterdayPriceRepository);

        Long id = 1L;
        Stock stock = getStock(id);
        LocalDate nowDate = LocalDate.now();
        Double rate = 0.1;

        when(priceChangeRateRepository.save(any(TradingPriceChangeRate.class))).thenReturn(TradingPriceChangeRate.of(rate, nowDate, stock));

        // when
        TradingPriceChangeRate tradingPriceChangeRate = service.savePriceChangeRate(stock, nowDate, rate);

        //then
        assertEquals(id, tradingPriceChangeRate.getEmpId().getStock().getId());
        assertEquals(nowDate, tradingPriceChangeRate.getEmpId().getTradingDate());
        assertEquals(rate, tradingPriceChangeRate.getRate());
    }

    @Test
    void saveTradingVolumeTest() throws NoSuchFieldException, IllegalAccessException {
        // give
        service = new TradingService(repository, priceChangeRateRepository, tradingVolumeRepository, yesterdayPriceRepository);

        Long id = 1L;
        Stock stock = getStock(id);
        LocalDate nowDate = LocalDate.now();
        int volume = 1000;
        when(tradingVolumeRepository.save(any(TradingVolume.class))).thenReturn(TradingVolume.of(nowDate, stock, volume));

        // when
        TradingVolume tradingVolume = service.saveTradingVolume(nowDate, stock, volume);

        assertEquals(id, tradingVolume.getEmpId().getStock().getId());
        assertEquals(nowDate, tradingVolume.getEmpId().getTradingDate());
        assertEquals(volume, tradingVolume.getVolume());
    }

    @Test
    void saveYesterdayPriceTest() throws NoSuchFieldException, IllegalAccessException {
        // give
        service = new TradingService(repository, priceChangeRateRepository, tradingVolumeRepository, yesterdayPriceRepository);

        Long id = 1L;
        Stock stock = getStock(id);
        LocalDate nowDate = LocalDate.now();
        int price = 5000;

        when(yesterdayPriceRepository.save(any(YesterdayPrice.class))).thenReturn(YesterdayPrice.of(stock, nowDate, price));
        // when
        YesterdayPrice yesterdayPrice = service.saveYestedayPrice(stock, nowDate, price);

        assertEquals(id, yesterdayPrice.getEmbId().getStock().getId());
        assertEquals(nowDate, yesterdayPrice.getEmbId().getTradingDate());
        assertEquals(price, yesterdayPrice.getPrice());
    }

    @Test
    void getYestdayPriceTest() throws NoSuchFieldException, IllegalAccessException {
        // give
        service = new TradingService(repository, priceChangeRateRepository, tradingVolumeRepository, yesterdayPriceRepository);

        Long id = 1L;
        Stock stock = getStock(id);
        LocalDate nowDate = LocalDate.now();
        int price = 5000;
        when(yesterdayPriceRepository.findByEmbId(any(StockTradingDateEmbId.class))).thenReturn(YesterdayPrice.of(stock, nowDate, price));
        // when
        YesterdayPrice yesterdayPrice = service.getYesterdayPrice(nowDate, stock);

        assertEquals(id, yesterdayPrice.getEmbId().getStock().getId());
        assertEquals(nowDate, yesterdayPrice.getEmbId().getTradingDate());
        assertEquals(price, yesterdayPrice.getPrice());

    }

}