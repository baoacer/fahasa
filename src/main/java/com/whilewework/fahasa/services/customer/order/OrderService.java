package com.whilewework.fahasa.services.customer.order;

import com.whilewework.fahasa.dto.OrderDto;
import jakarta.transaction.Transactional;

public interface OrderService {
    @Transactional
    void placeOrder(OrderDto orderDto);
}
