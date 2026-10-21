package com.example.malladmin.service;

import com.example.malladmin.dto.MallOrderDTO;

import java.util.List;

/**
 * 商城订单管理服务接口
 */
public interface MallOrderService {

    /**
     * 查询所有订单
     *
     * @return 订单DTO列表
     */
    List<MallOrderDTO> listAll();

    /**
     * 查询订单详情
     *
     * @param id 订单ID
     * @return 订单DTO
     */
    MallOrderDTO detail(Long id);

    /**
     * 修改订单状态
     *
     * @param id     订单ID
     * @param status 目标状态
     * @return 更新后的订单DTO
     */
    MallOrderDTO changeStatus(Long id, String status);

    /**
     * 根据手机号查询订单列表
     *
     * @param phone 手机号
     * @return 订单DTO列表
     */
    List<MallOrderDTO> listByPhone(String phone);
}
