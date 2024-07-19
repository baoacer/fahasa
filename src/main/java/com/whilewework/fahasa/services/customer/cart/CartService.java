package com.whilewework.fahasa.services.customer.cart;

import com.whilewework.fahasa.dto.AddProductInCartDto;
import com.whilewework.fahasa.entity.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<?> addToCart(Long productId);

    List<Cart> getCartDetails();

    ResponseEntity<?> deleteCartDetails(Long cartId);
}
