package com.choru.stock.stock.service;

import com.choru.stock.common.exception.RestApiException;
import com.choru.stock.common.exception.RestApiExceptionCode;
import com.choru.stock.stock.entity.Stock;
import com.choru.stock.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository repository;

    public Stock getStock(Long code){
        Stock stock = repository.findByCode(code);
        if(ObjectUtils.isEmpty(stock)){
            throw new RestApiException(RestApiExceptionCode.BAD_REQUEST, "주식 정보가 없습니다.");
        }
        return stock;
    }

    public void updatePrice(Long id, int price){
        Stock stock = repository.findById(id)
                                .orElseThrow(() -> new RestApiException(RestApiExceptionCode.BAD_REQUEST, "주식 정보가 없습니다."));
        stock.updatePrice(price);
    }

    public List<Stock> getStocks(List<Long> codes){
        return repository.findAllByCodeIn(codes);
    }
}
