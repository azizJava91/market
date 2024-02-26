package com.market.repository;

import com.market.entity.Cart;
import com.market.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByCartIdAndActive(Long cartId, Integer active);
    List<Cart> findAllByCustomerAndActive(Customer customer, Integer active);
}
