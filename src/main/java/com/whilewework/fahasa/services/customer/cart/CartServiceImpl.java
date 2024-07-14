package com.whilewework.fahasa.services.customer.cart;

import com.whilewework.fahasa.dto.AddProductInCartDto;
import com.whilewework.fahasa.dto.CartItemsDto;
import com.whilewework.fahasa.dto.OrderDto;
import com.whilewework.fahasa.entity.*;
import com.whilewework.fahasa.enums.OrderStatus;
import com.whilewework.fahasa.exceptions.ValidationException;
import com.whilewework.fahasa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AdminCouponRepository couponRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        // Find a user's current orders that have a pending status
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);

        if (activeOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Active order not found");
        }

        // Check if the product is in the cart of the current order?
        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        // If the product is already in the cart, return CONFLICT status
        if(optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else{
            // If the product is not in the cart

            // Find Product and User from database
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            // If Product and User is already, Add the product to the cart
            if(optionalProduct.isPresent() && optionalUser.isPresent()){
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart =  cartItemRepository.save(cart);

                // Update the order amount and total amount
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            }else{
                // If Product or User is not found, return NOT_FOUND status
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
            }
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        if (activeOrder == null) {
            return null;
        }
        List<CartItemsDto> cartItemsDtos = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setId(activeOrder.getId());
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setCartItems(cartItemsDtos);

        if(activeOrder.getCoupon() != null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    @Override
    public OrderDto applyCoupon(Long userId, String code){
        // Find a user's current orders that have a pending status'
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        if (activeOrder == null) {
            return null;
        }

        // Find Coupon from database by code
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found"));

        // Check coupon is expired
        if(couponIsExpired(coupon)){
            throw new ValidationException(("Coupon has expired"));
        }

        // Apply coupon to the order
        double discountAmount =  ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        // Update orders with discount
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setAmount((long) netAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }


    // check coupon is expired ?
    private boolean couponIsExpired(Coupon coupon) {
        Date currDate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && currDate.after(expirationDate);
    }

}
