package com.market.repository;

import com.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserId(Long userId);

    User findUserByUsernameAndPasswordAndActive(String username, String password, Integer active);
}
