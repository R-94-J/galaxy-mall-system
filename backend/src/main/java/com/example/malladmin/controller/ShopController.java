package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.dto.CustomerDTO;
import com.example.malladmin.dto.MallOrderDTO;
import com.example.malladmin.dto.shop.ShopLoginRequest;
import com.example.malladmin.dto.shop.ShopOrderRequest;
import com.example.malladmin.dto.shop.ShopRegisterRequest;
import com.example.malladmin.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商城用户端控制器，处理会员注册、登录、下单等操作
 */
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    /**
     * 会员注册
     */
    @PostMapping("/register")
    public ApiResponse<CustomerDTO> register(@Valid @RequestBody ShopRegisterRequest request) {
        return ApiResponse.ok(shopService.register(request));
    }

    /**
     * 会员登录
     */
    @PostMapping("/login")
    public ApiResponse<CustomerDTO> login(@Valid @RequestBody ShopLoginRequest request) {
        return ApiResponse.ok(shopService.login(request));
    }

    /**
     * 创建订单
     */
    @PostMapping("/orders")
    public ApiResponse<MallOrderDTO> createOrder(@Valid @RequestBody ShopOrderRequest request) {
        return ApiResponse.ok(shopService.createOrder(request));
    }

    /**
     * 根据手机号查询订单列表
     */
    @GetMapping("/orders")
    public ApiResponse<List<MallOrderDTO>> orders(@RequestParam String phone) {
        return ApiResponse.ok(shopService.listOrdersByPhone(phone));
    }
}
