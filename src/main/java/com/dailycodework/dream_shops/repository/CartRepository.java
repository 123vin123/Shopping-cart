package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart ,Long> {
    Cart findByUserId(Long userId);
//    void findByUserId(Long id);
}
