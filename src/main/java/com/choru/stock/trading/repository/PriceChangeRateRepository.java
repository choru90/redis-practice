package com.choru.stock.trading.repository;

import com.choru.stock.trading.entity.TradingPriceChangeRate;
import com.choru.stock.trading.entity.StockTradingDateEmbId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceChangeRateRepository extends JpaRepository<TradingPriceChangeRate, StockTradingDateEmbId> {

    TradingPriceChangeRate findByEmpId(StockTradingDateEmbId empId);

}
