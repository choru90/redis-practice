package com.choru.stock.stock.usecase;

import com.choru.stock.common.code.RankTagType;
import com.choru.stock.common.component.RedisComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockUsecase {
    private final RedisComponent redisComponent;
    private final int POPULARITY_INCREMENT_COUNT = 1;

    public String readStock(Long code){
        String key = RankTagType.POPULARITY.getKey();
        Double score = redisComponent.incrementScore(key, code.toString(), POPULARITY_INCREMENT_COUNT);
        redisComponent.expireOneDay(key);
        return "complete score: " + score.intValue();
    }

}
