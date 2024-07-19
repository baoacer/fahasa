package com.whilewework.fahasa.services.customer.order;

import com.whilewework.fahasa.dto.OrderDto;
import com.whilewework.fahasa.dto.OrderProduct;
import com.whilewework.fahasa.entity.Order;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.entity.User;
import com.whilewework.fahasa.filters.JwtRequestFilter;
import com.whilewework.fahasa.repository.OrderRepository;
import com.whilewework.fahasa.repository.ProductRepository;
import com.whilewework.fahasa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public void placeOrder(OrderDto orderDto) {
        // Find user
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create Order entity
        Order order = new Order();
        order.setOrderFullName(orderDto.getFullName());
        order.setOrderAddress(orderDto.getFullAddress());
        order.setOrderContactNumber(orderDto.getContactNumber());
        order.setOrderStatus("Placed");
        order.setUser(user);

        List<Product> products = new ArrayList<>();
        double totalAmount = 0.0;

        // Process each order product
        for (OrderProduct orderProduct : orderDto.getOrderProducts()) {
            // Find product
            Product product = productRepository.findById(orderProduct.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            products.add(product);
            totalAmount += product.getPrice() * orderProduct.getQuantity();
        }

        // Set products and order amount
        order.setProducts(products);
        order.setOrderAmount(totalAmount);

        // Save the order
        orderRepository.save(order);
    }

}

