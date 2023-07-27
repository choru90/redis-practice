package com.choru.stock.rank.usecase;

import com.choru.stock.common.code.RankTagType;
import com.choru.stock.common.code.SortType;
import com.choru.stock.common.component.RedisComponent;
import com.choru.stock.rank.dto.RankRes;
import com.choru.stock.stock.entity.Stock;
import com.choru.stock.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankingUsecaseTest {

    private RankingUsecase usecase;

    @Mock
    private StockService stockService;
    @Mock
    private RedisComponent redisComponent;


    @Test
    void getListCodeIsNullTest(){
        //given
        usecase = new RankingUsecase(stockService, redisComponent);
        RankTagType type = RankTagType.POPULARITY;
        SortType sortType = SortType.ACS;
        int size = 0;
        int page = 100;
        int startIndex= page * size;
        int endIndex = (page * size) +size;

        when(redisComponent.rangeBySort(type.getKey(), startIndex, endIndex, sortType)).thenReturn(null);

        // when
        List<RankRes> result = usecase.getList(type, SortType.ACS, size, page);
        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void getListStockIsNullTest() throws NoSuchFieldException, IllegalAccessException {
        //given
        usecase = new RankingUsecase(stockService, redisComponent);
        RankTagType type = RankTagType.POPULARITY;
        SortType sortType = SortType.ACS;
        int size = 0;
        int page = 100;

        Long code1 = 100L;
        Long code2 = 200L;
        Long code3 = 300L;

        List<Long> codes = List.of(code1, code2, code3);
        int startIndex= page * size;
        int endIndex = (page * size) +size;

        when(redisComponent.rangeBySort(type.getKey(), startIndex, endIndex, sortType)).thenReturn(codes);
        when(stockService.getStocks(codes)).thenReturn(null);

        // when
        List<RankRes> result = usecase.getList(type, SortType.ACS, size, page);
        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void getListTest() throws IllegalAccessException, NoSuchFieldException {
        //given
        usecase = new RankingUsecase(stockService, redisComponent);
        RankTagType type = RankTagType.POPULARITY;
        SortType sortType = SortType.ACS;
        int size = 0;
        int page = 100;

        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;

        Long code1 = 100L;
        Long code2 = 200L;
        Long code3 = 300L;

        String name1 = "이름1";
        String name2 = "이름2";
        String name3 = "이름3";

        int price1 = 1000;
        int price2 = 2000;
        int price3 = 3000;

        List<Long> codes = List.of(code1, code2, code3);
        int startIndex= page * size;
        int endIndex = (page * size) +size;

        Stock stock1 = new Stock();
        Stock stock2 = new Stock();
        Stock stock3 = new Stock();

        Field idField = Stock.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(stock1, id1);
        idField.set(stock2, id2);
        idField.set(stock3, id3);

        Field codeField = Stock.class.getDeclaredField("code");
        codeField.setAccessible(true);
        codeField.set(stock1, code1);
        codeField.set(stock2, code2);
        codeField.set(stock3, code3);

        Field nameField = Stock.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(stock1, name1);
        nameField.set(stock2, name2);
        nameField.set(stock3, name3);

        Field priceField = Stock.class.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(stock1, price1);
        priceField.set(stock2, price2);
        priceField.set(stock3, price3);

        Double rate1 = 0.1;
        Double rate2 = 0.152;

        when(redisComponent.rangeBySort(type.getKey(), startIndex, endIndex, sortType)).thenReturn(codes);
        when(stockService.getStocks(codes)).thenReturn(List.of(stock1, stock2, stock3));
        when(redisComponent.score(RankTagType.TRADING_PRICE_RATE.getKey(), stock1.getCode().toString())).thenReturn(null);
        when(redisComponent.score(RankTagType.TRADING_PRICE_RATE.getKey(), stock2.getCode().toString())).thenReturn(rate1);
        when(redisComponent.score(RankTagType.TRADING_PRICE_RATE.getKey(), stock3.getCode().toString())).thenReturn(rate2);

        // when
        List<RankRes> result = usecase.getList(type, SortType.ACS, size, page);

        // then
        assertEquals(id1, result.get(0).getId());
        assertEquals(code1, result.get(0).getCode());
        assertEquals(name1, result.get(0).getName());
        assertEquals(price1, result.get(0).getPrice());
        assertEquals(0, result.get(0).getRate());

        assertEquals(id2, result.get(1).getId());
        assertEquals(code2, result.get(1).getCode());
        assertEquals(name2, result.get(1).getName());
        assertEquals(price2, result.get(1).getPrice());
        assertEquals(rate1, result.get(1).getRate());

        assertEquals(id3, result.get(2).getId());
        assertEquals(code3, result.get(2).getCode());
        assertEquals(name3, result.get(2).getName());
        assertEquals(price3, result.get(2).getPrice());
        assertEquals(Double.parseDouble(String.format("%.2f",rate2)), result.get(2).getRate());
    }




}