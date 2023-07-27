package com.choru.stock.common.component;

import com.choru.stock.common.code.SortType;
import com.choru.stock.common.utils.ParseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisComponentImpl implements RedisComponent{
    private final StringRedisTemplate template;

    @Override
    public List<Long> rangeBySort(String key, long start, long end, SortType sortType) {
        if(SortType.ACS.equals(sortType)){
            return reverseRange(key, start, end);
        }
        return range(key, start, end);
    }

    @Override
    public List<Long> reverseRange(String key, long start, long end) {
        Set<String> strCodes = template.opsForZSet().reverseRange(key, start, end);
        return ParseUtils.changeFromStringToLong(new ArrayList<>(strCodes));
    }

    @Override
    public List<Long> range(String key, long start, long end) {
        Set<String> strCodes = template.opsForZSet().range(key, start, end);
        return ParseUtils.changeFromStringToLong(new ArrayList<>(strCodes));
    }

    @Override
    public Double score(String key, String value) {
        return template.opsForZSet().score(key, value);
    }

    @Override
    public Double incrementScore(String key, String value, double score) {
        Double savedScore = template.opsForZSet().incrementScore(key, value, score);
        return savedScore;
    }

    @Override
    public Boolean add(String key, String value, double score) {
        Boolean result = template.opsForZSet().add(key, value, score);
        return result;
    }

    @Override
    public void expireOneDay(String key) {
        LocalDateTime stockTradStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0, 0));
        template.expire(key,Duration.between(stockTradStartTime, stockTradStartTime.plusDays(1).minusNanos(1)));
    }
}
