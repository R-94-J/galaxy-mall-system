package com.example.malladmin.service;

import com.example.malladmin.dto.ProductDTO;

import java.util.List;

/**
 * 商品管理服务接口
 */
public interface ProductService {

    /**
     * 查询所有商品
     *
     * @return 商品DTO列表
     */
    List<ProductDTO> listAll();

    /**
     * 创建商品
     *
     * @param dto 商品DTO
     * @return 创建后的商品DTO
     */
    ProductDTO create(ProductDTO dto);

    /**
     * 更新商品
     *
     * @param id  商品ID
     * @param dto 商品DTO
     * @return 更新后的商品DTO
     */
    ProductDTO update(Long id, ProductDTO dto);

    /**
     * 修改商品上下架状态
     *
     * @param id     商品ID
     * @param status 目标状态
     * @return 更新后的商品DTO
     */
    ProductDTO changeStatus(Long id, String status);

    /**
     * 删除商品
     *
     * @param id 商品ID
     */
    void delete(Long id);
}
