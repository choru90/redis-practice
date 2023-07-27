package com.choru.stock.stock.controller;

import com.choru.stock.stock.usecase.StockUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주식", description = "주식 인기순 태그를 위한 정보 저장 API")
@RestController
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {
    private final StockUsecase usecase;


    @Operation(summary = "주식을 조회", description = "주식을 조회시에 조회 기록을 위해 불리는 API")
    @PostMapping("read")
    public String readStock(@RequestParam("code")Long code){
        return usecase.readStock(code);
    }


}
