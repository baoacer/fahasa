package com.whilewework.fahasa.services.customer.cart;

import com.whilewework.fahasa.dto.AddProductInCartDto;
import com.whilewework.fahasa.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);
}
