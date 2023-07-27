package com.choru.stock.trading.repository;

import com.choru.stock.trading.entity.TradingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingRecordRepository extends JpaRepository<TradingRecord, Long> {
}
