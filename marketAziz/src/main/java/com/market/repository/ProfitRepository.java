package com.market.repository;

import com.market.entity.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long> {
    List<Profit> findAllByActive(Integer active);
}
