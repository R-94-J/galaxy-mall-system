package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.dto.CouponDTO;
import com.example.malladmin.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 优惠券管理控制器
 */
@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * 查询所有优惠券
     */
    @GetMapping
    public ApiResponse<List<CouponDTO>> list() {
        return ApiResponse.ok(couponService.listAll());
    }

    /**
     * 创建优惠券
     */
    @PostMapping
    public ApiResponse<CouponDTO> create(@Valid @RequestBody CouponDTO dto) {
        return ApiResponse.ok(couponService.create(dto));
    }

    /**
     * 更新优惠券
     */
    @PutMapping("/{id}")
    public ApiResponse<CouponDTO> update(@PathVariable Long id,
                                         @Valid @RequestBody CouponDTO dto) {
        return ApiResponse.ok(couponService.update(id, dto));
    }
}
