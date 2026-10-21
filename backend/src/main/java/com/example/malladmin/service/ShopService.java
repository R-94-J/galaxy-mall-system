package com.example.malladmin.service;

import com.example.malladmin.dto.CustomerDTO;
import com.example.malladmin.dto.MallOrderDTO;
import com.example.malladmin.dto.shop.ShopLoginRequest;
import com.example.malladmin.dto.shop.ShopOrderRequest;
import com.example.malladmin.dto.shop.ShopRegisterRequest;

import java.util.List;

/**
 * 商城用户端服务接口，处理会员注册、登录、下单等操作
 */
public interface ShopService {

    /**
     * 会员注册
     *
     * @param request 注册请求
     * @return 注册后的会员DTO
     */
    CustomerDTO register(ShopRegisterRequest request);

    /**
     * 会员登录
     *
     * @param request 登录请求
     * @return 登录后的会员DTO
     */
    CustomerDTO login(ShopLoginRequest request);

    /**
     * 创建订单
     *
     * @param request 下单请求
     * @return 创建后的订单DTO
     */
    MallOrderDTO createOrder(ShopOrderRequest request);

    /**
     * 根据手机号查询会员订单
     *
     * @param phone 手机号
     * @return 订单DTO列表
     */
    List<MallOrderDTO> listOrdersByPhone(String phone);
}
