package com.foododeringplatform.foododeringplatform.repository;

import com.foododeringplatform.foododeringplatform.model.Order;
import com.foododeringplatform.foododeringplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
}
