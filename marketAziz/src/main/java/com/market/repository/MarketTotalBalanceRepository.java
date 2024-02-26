package com.market.repository;

import com.market.entity.MarketTotalBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketTotalBalanceRepository extends JpaRepository<MarketTotalBalance,Long> {
    MarketTotalBalance findByIdAndActive(Long id, Integer active);
}
