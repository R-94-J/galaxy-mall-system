package com.example.malladmin.service;

import com.example.malladmin.dto.CategoryDTO;

import java.util.List;

/**
 * 商品分类服务接口
 */
public interface CategoryService {

    /**
     * 查询所有分类
     *
     * @return 分类DTO列表
     */
    List<CategoryDTO> listAll();

    /**
     * 创建分类
     *
     * @param dto 分类DTO
     * @return 创建后的分类DTO
     */
    CategoryDTO create(CategoryDTO dto);

    /**
     * 更新分类
     *
     * @param id  分类ID
     * @param dto 分类DTO
     * @return 更新后的分类DTO
     */
    CategoryDTO update(Long id, CategoryDTO dto);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void delete(Long id);
}
