package com.whilewework.fahasa.repository;

import com.whilewework.fahasa.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminCouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByCode(String code);
}
