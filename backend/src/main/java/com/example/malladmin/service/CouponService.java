package com.example.malladmin.service;

import com.example.malladmin.dto.CouponDTO;

import java.util.List;

/**
 * 优惠券管理服务接口
 */
public interface CouponService {

    /**
     * 查询所有优惠券
     *
     * @return 优惠券DTO列表
     */
    List<CouponDTO> listAll();

    /**
     * 创建优惠券
     *
     * @param dto 优惠券DTO
     * @return 创建后的DTO
     */
    CouponDTO create(CouponDTO dto);

    /**
     * 更新优惠券
     *
     * @param id  优惠券ID
     * @param dto 优惠券DTO
     * @return 更新后的DTO
     */
    CouponDTO update(Long id, CouponDTO dto);
}
