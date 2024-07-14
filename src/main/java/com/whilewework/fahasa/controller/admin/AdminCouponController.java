package com.whilewework.fahasa.controller.admin;

import com.whilewework.fahasa.dto.CouponDto;
import com.whilewework.fahasa.entity.Coupon;
import com.whilewework.fahasa.exceptions.ValidationException;
import com.whilewework.fahasa.repository.AdminCouponRepository;
import com.whilewework.fahasa.services.admin.coupon.AdminCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/coupon")
@RequiredArgsConstructor
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponDto couponDto){
        try {
            CouponDto createCoupon = adminCouponService.createCoupon(couponDto);
            return ResponseEntity.ok(createCoupon);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok( adminCouponService.getAllCoupons());
    }



}
