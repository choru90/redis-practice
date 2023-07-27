package com.choru.stock.stock.usecase;

import com.choru.stock.common.code.RankTagType;
import com.choru.stock.common.component.RedisComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockUsecaseTest {

    private StockUsecase usecase;

    @Mock
    private RedisComponent redisComponent;

    @Test
    void readStockTest(){
        // given
        usecase = new StockUsecase(redisComponent);
        Long code = 200L;
        Double score = 1.0;

        when(redisComponent.incrementScore(RankTagType.POPULARITY.getKey(), code.toString(), 1)).thenReturn(score);
        doNothing().when(redisComponent).expireOneDay(RankTagType.POPULARITY.getKey());
        // when
        String result = usecase.readStock(code);
        // then
        assertEquals("complete score: "+ score.intValue(), result);
        verify(redisComponent, times(1)).incrementScore(RankTagType.POPULARITY.getKey(), code.toString(), 1);
        verify(redisComponent, times(1)).expireOneDay(RankTagType.POPULARITY.getKey());
    }

}