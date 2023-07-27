package com.choru.stock.stock.repository;

import com.choru.stock.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>{
    List<Stock> findAllByCodeIn(List<Long> codes);
    Stock findByCode(Long code);
}
