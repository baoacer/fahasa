package com.whilewework.fahasa.repository;

import com.whilewework.fahasa.entity.Order;
import com.whilewework.fahasa.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
