package com.choru.stock.trading.repository;

import com.choru.stock.trading.entity.StockTradingDateEmbId;
import com.choru.stock.trading.entity.TradingVolume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingVolumeRepository extends JpaRepository<TradingVolume, StockTradingDateEmbId> {
}
