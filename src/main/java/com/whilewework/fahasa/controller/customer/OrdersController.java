package com.whilewework.fahasa.controller.customer;

import com.whilewework.fahasa.dto.OrderDto;
import com.whilewework.fahasa.services.customer.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody OrderDto orderDto){
        orderService.placeOrder(orderDto);
    }

}
