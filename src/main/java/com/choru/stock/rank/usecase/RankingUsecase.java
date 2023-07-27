package com.choru.stock.rank.usecase;

import com.choru.stock.common.code.RankTagType;
import com.choru.stock.common.code.SortType;
import com.choru.stock.common.component.RedisComponent;
import com.choru.stock.rank.dto.RankRes;
import com.choru.stock.stock.entity.Stock;
import com.choru.stock.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingUsecase {

    private final StockService stockService;
    private final RedisComponent redisComponent;

    public List<RankRes> getList(RankTagType type, SortType sortType, int size, int page){
        List<Long> codes = getCodes(type, sortType, size, page);
        return  getRankResList(getStocks(codes), codes);
    }

    private List<Stock> getStocks(List<Long> codes){
        if(ObjectUtils.isEmpty(codes)){
            return Collections.emptyList();
        }
        return stockService.getStocks(codes);
    }

    private List<RankRes> getRankResList(List<Stock> stocks, List<Long> codes){
        if(ObjectUtils.isEmpty(stocks)){
            return Collections.emptyList();
        }

        return stocks.stream()
                .map(stock -> RankRes.of(stock,
                        getRate(stock),
                        codes.indexOf(stock.getCode())))
                .sorted(Comparator.comparing(RankRes::getIndex))
                .toList();
    }

    private Double getRate(Stock stock){
        return ObjectUtils.isEmpty(redisComponent.score(RankTagType.TRADING_PRICE_RATE.getKey(), stock.getCode().toString())) ?
                0 : round(redisComponent.score(RankTagType.TRADING_PRICE_RATE.getKey(), stock.getCode().toString()));
    }

    private Double round(Double score){
        return Double.parseDouble(String.format("%.2f",score));
    }

    private List<Long> getCodes(RankTagType type, SortType sortType, int size, int page) {
        int startIndex = page * size;
        int endIndex = (page * size) + size;
        return redisComponent.rangeBySort(type.getKey(), startIndex, endIndex, sortType);
    }


}
