package com.choru.stock.trading.repository;

import com.choru.stock.trading.entity.StockTradingDateEmbId;
import com.choru.stock.trading.entity.YesterdayPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YesterdayPriceRepository extends JpaRepository<YesterdayPrice, StockTradingDateEmbId> {

    YesterdayPrice findByEmbId(StockTradingDateEmbId embId);

}
