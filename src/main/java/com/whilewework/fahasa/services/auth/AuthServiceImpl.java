package com.whilewework.fahasa.services.auth;

import com.whilewework.fahasa.dto.SignupRequest;
import com.whilewework.fahasa.dto.UserDto;
import com.whilewework.fahasa.entity.Order;
import com.whilewework.fahasa.entity.User;
import com.whilewework.fahasa.enums.OrderStatus;
import com.whilewework.fahasa.enums.UserRole;
import com.whilewework.fahasa.repository.OrderRepository;
import com.whilewework.fahasa.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());
        userDto.setEmail(createUser.getEmail());
        userDto.setName(createUser.getName());
        userDto.setUserRole(createUser.getRole());

        return userDto;
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount == null){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            userRepository.save(user);
        }
    }

}