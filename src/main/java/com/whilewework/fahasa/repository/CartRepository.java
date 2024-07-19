package com.whilewework.fahasa.repository;

import com.whilewework.fahasa.entity.Cart;
import com.whilewework.fahasa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    List<Cart> findByUser(Optional<User> user);
}
