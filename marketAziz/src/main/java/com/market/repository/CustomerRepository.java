package com.market.repository;

import com.market.entity.Customer;
import com.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUserAndActive(User user, Integer active);
    Customer findByCustomerIdAndActive(Long customerId, Integer active);
}
