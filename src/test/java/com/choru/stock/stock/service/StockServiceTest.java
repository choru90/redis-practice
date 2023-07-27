package com.choru.stock.stock.service;

import com.choru.stock.common.exception.RestApiException;
import com.choru.stock.common.exception.RestApiExceptionCode;
import com.choru.stock.stock.entity.Stock;
import com.choru.stock.stock.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    private StockService service;
    @Mock
    StockRepository repository;



    /*
    * case1. 주식이 있는 경우
    * case2. 주식이 없는 경우 오류 발생
    *
    * */
    @Test
    void getStockTest(){
        service = new StockService(repository);

        Stock stock = new Stock();
        Long code = 270L;
        // given
        when(repository.findByCode(eq(code))).thenReturn(stock);
        // when
        Stock resultStock = service.getStock(code);
        // then
        assertEquals(stock, resultStock);
    }

    @Test
    void getStockNoStockTest(){
        service = new StockService(repository);

        Stock stock = new Stock();
        Long code = 270L;
        // given
        when(repository.findByCode(eq(code))).thenReturn(null);

        try{
            // when
            service.getStock(code);
        } catch (RestApiException e){
            // then
            assertEquals(RestApiExceptionCode.BAD_REQUEST, e.getCode());
            assertEquals("주식 정보가 없습니다.",e.getMessage());
        }

    }

    @Test
    void updatePriceTest(){
        service = new StockService(repository);

        // given
        Stock stock = new Stock();
        Long id = 1L;
        int price = 5000;
        when(repository.findById(eq(id))).thenReturn(Optional.of(stock));

       // when
        service.updatePrice(id, price);

        // then
        assertEquals(price, stock.getPrice());
    }

    @Test
    void updatePriceStockIsNullTest(){
        service = new StockService(repository);

        // given
        Stock stock = new Stock();
        Long id = 1L;
        int price = 5000;
        when(repository.findById(eq(id))).thenReturn(Optional.ofNullable(null));

        // when
        try{
            service.updatePrice(id, price);
        } catch (RestApiException e){
            // then
            assertEquals(RestApiExceptionCode.BAD_REQUEST, e.getCode());
            assertEquals("주식 정보가 없습니다.", e.getMessage());
        }
    }

    @Test
    void getStocks() throws IllegalAccessException, NoSuchFieldException {
        service = new StockService(repository);
        Long id1 = 1L;
        Long id2 = 2L;

        List<Long> codes = List.of(id1, id2);

        Stock stock1 = new Stock();
        Stock stock2 = new Stock();
        Field idField = stock1.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(stock1, id1);
        idField.set(stock2, id2);

        List<Stock> stocks = List.of(stock1, stock2);
        when(repository.findAllByCodeIn(eq(codes))).thenReturn(stocks);

        // given
        List<Stock> resultStocks = service.getStocks(codes);

        assertEquals(id1, resultStocks.get(0).getId());
        assertEquals(id2, resultStocks.get(1).getId());
    }



}