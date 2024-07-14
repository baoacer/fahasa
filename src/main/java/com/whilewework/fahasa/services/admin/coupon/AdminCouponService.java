package com.whilewework.fahasa.services.admin.coupon;

import com.whilewework.fahasa.dto.CouponDto;
import com.whilewework.fahasa.entity.Coupon;
import jakarta.xml.bind.ValidationException;

import java.util.List;

public interface AdminCouponService {
    // create new coupon
    CouponDto createCoupon(CouponDto couponDto);

    List<Coupon> getAllCoupons();
}
