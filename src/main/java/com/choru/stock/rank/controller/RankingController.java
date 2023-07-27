package com.choru.stock.rank.controller;

import com.choru.stock.common.code.RankTagType;
import com.choru.stock.common.code.SortType;
import com.choru.stock.rank.dto.RankRes;
import com.choru.stock.rank.usecase.RankingUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "랭킹", description = "주식 태그 조회를 위한 API")
@RestController
@RequestMapping("ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingUsecase usecase;

    @Operation(summary = "랭킹 조회", description = "랭킹 조회를 위해 type과 sort조합해야합니다   </br>" +
            "인기순 : type = POPULARITY, sort=ASC  </br>" +
            "상승 : type =TRADING_PRICE_RATE, sort=ASC  </br>" +
            "하락 : type =TRADING_PRICE_RATE, sort=DESC  </br>" +
            "거래량 : type =TRADING_VOLUME, sort=ASC  </br>"
    )
    @GetMapping("{type}")
    public List<RankRes> getList(@PathVariable("type") RankTagType type,
                                 @RequestParam("sort") SortType sort,
                                 @RequestParam("page") int page,
                                 @RequestParam("size") int size){
        return usecase.getList(type, sort, size, page);

    }




}
