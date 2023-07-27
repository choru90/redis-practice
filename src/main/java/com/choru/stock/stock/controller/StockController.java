package com.choru.stock.stock.controller;

import com.choru.stock.stock.service.StockService;
import com.choru.stock.stock.usecase.StockUsecase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "주식")
@RestController
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {
    private final StockUsecase usecase;

    @PostMapping("read")
    public String readStock(@RequestParam("code")Long code){
        return usecase.readStock(code);
    }


}
