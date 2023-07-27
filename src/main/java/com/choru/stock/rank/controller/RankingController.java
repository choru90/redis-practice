package com.choru.stock.rank.controller;

import com.choru.stock.common.code.RankTagType;
import com.choru.stock.common.code.SortType;
import com.choru.stock.rank.dto.RankRes;
import com.choru.stock.rank.usecase.RankingUsecase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "랭킹")
@RestController
@RequestMapping("ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingUsecase usecase;

    @GetMapping("{type}")
    public List<RankRes> getList(@PathVariable("type") RankTagType type,
                                 @RequestParam("sort") SortType sort,
                                 @RequestParam("page") int page,
                                 @RequestParam("size") int size){
        return usecase.getList(type, sort, size, page);

    }




}
