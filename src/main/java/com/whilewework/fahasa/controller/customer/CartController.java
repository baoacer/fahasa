package com.whilewework.fahasa.controller.customer;

import com.whilewework.fahasa.entity.Cart;
import com.whilewework.fahasa.services.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Long productId){
        return cartService.addToCart(productId);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getCartDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartDetails());
    }

    @DeleteMapping("/cart/delete/{cartId}")
    public ResponseEntity<?> deleteCartDetails(@PathVariable Long cartId) {
        return cartService.deleteCartDetails(cartId);
    }


}
