package com.whilewework.fahasa.services.admin.coupon;

import com.whilewework.fahasa.dto.CouponDto;
import com.whilewework.fahasa.entity.Coupon;
import com.whilewework.fahasa.exceptions.ValidationException;
import com.whilewework.fahasa.repository.AdminCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final AdminCouponRepository adminCouponRepository;

    // create new coupon
    @Override
    public CouponDto createCoupon(CouponDto couponDto) {

        Coupon coupon = new Coupon();
        coupon.setCode(couponDto.getCode());
        coupon.setDiscount(couponDto.getDiscount());
        coupon.setName(couponDto.getName());
        coupon.setExpirationDate(couponDto.getExpirationDate());

        if(adminCouponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon already exists");
        }
        adminCouponRepository.save(coupon);

        return couponDto;
    }

    @Override
    public List<Coupon> getAllCoupons(){
        return adminCouponRepository.findAll();
    }


}
