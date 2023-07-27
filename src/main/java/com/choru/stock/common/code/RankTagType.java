package com.choru.stock.common.code;

import lombok.Getter;

@Getter
public enum RankTagType {
    POPULARITY("popularity"),
    TRADING_PRICE_RATE("trading_price_rate"),
    TRADING_VOLUME("trading_volume");

    private String key;


    RankTagType(String key){
        this.key = key;

    }


}
