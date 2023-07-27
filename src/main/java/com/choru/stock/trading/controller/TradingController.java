package com.choru.stock.trading.controller;

import com.choru.stock.trading.usecase.TradingUsecase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "거래", description = "주식 태그 조회에 값을 변경하기 위한 API")
@RestController
@RequestMapping("trading")
@RequiredArgsConstructor
public class TradingController {
    private final TradingUsecase usecase;

    @PostMapping
    public void addTrading(@RequestParam("code")Long code,
                           @RequestParam("count")int tradingCount,
                           @RequestParam("price")int price){
        usecase.addTradingRecord(code, tradingCount, price);
    }
}
