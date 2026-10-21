package com.example.malladmin.service;

import com.example.malladmin.dto.CustomerDTO;

import java.util.List;

/**
 * 商城会员管理服务接口
 */
public interface CustomerService {

    /**
     * 查询所有会员
     *
     * @return 会员DTO列表
     */
    List<CustomerDTO> listAll();

    /**
     * 创建会员
     *
     * @param dto 会员DTO
     * @return 创建后的会员DTO
     */
    CustomerDTO create(CustomerDTO dto);

    /**
     * 更新会员信息
     *
     * @param id  会员ID
     * @param dto 会员DTO
     * @return 更新后的会员DTO
     */
    CustomerDTO update(Long id, CustomerDTO dto);
}
