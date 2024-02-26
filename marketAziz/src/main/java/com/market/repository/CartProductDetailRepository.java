package com.market.repository;

import com.market.entity.CartProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductDetailRepository extends JpaRepository<CartProductDetail, Long> {
}
