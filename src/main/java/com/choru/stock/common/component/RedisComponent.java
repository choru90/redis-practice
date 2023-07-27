package com.choru.stock.common.component;

import com.choru.stock.common.code.SortType;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisComponent {

    List<Long> rangeBySort(String key, long start, long end, SortType sortType);
    List<Long> reverseRange(String key, long start, long end);

    List<Long> range(String key, long start, long end);

    Double score(String key, String value);

    Double incrementScore(String key, String value, double score);

    Boolean add(String key, String value, double score);

    void expireOneDay(String key);
}
