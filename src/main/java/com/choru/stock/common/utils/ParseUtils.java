package com.choru.stock.common.utils;

import java.util.List;

public class ParseUtils {

    public static List<Long> changeFromStringToLong(List<String> codes){
        return codes.stream().map(Long::parseLong).toList();
    }
}
