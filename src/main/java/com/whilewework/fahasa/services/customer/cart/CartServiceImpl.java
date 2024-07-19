package com.whilewework.fahasa.services.customer.cart;

import com.whilewework.fahasa.entity.Cart;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.entity.User;
import com.whilewework.fahasa.filters.JwtRequestFilter;
import com.whilewework.fahasa.repository.CartRepository;
import com.whilewework.fahasa.repository.ProductRepository;
import com.whilewework.fahasa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;;

    @Override
    public ResponseEntity<?> addToCart(Long productId){

        // get product and user from database
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        String username = JwtRequestFilter.CURRENT_USER;

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // add product to cart
        Cart cart = new Cart();

        cart.setUser(optionalUser.get());
        cart.setProduct(optionalProduct.get());

        cartRepository.save(cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @Override
    public List<Cart> getCartDetails(){
        String username = JwtRequestFilter.CURRENT_USER;
        Optional<User> user = userRepository.findByUsername(username);

        return cartRepository.findByUser(user);
    }

    @Override
    public ResponseEntity<?> deleteCartDetails(Long cartId) {
        String username = JwtRequestFilter.CURRENT_USER;
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        if (!cart.get().getUser().equals(optionalUser.get())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this cart");
        }

        cartRepository.delete(cart.get());
        return ResponseEntity.status(HttpStatus.OK).body("Cart deleted successfully");
    }


}
