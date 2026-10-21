package com.example.malladmin.service;

import com.example.malladmin.dto.InventoryLogDTO;

import java.util.List;

/**
 * 库存管理服务接口
 */
public interface InventoryLogService {

    /**
     * 查询所有库存流水
     *
     * @return 库存流水DTO列表
     */
    List<InventoryLogDTO> listAll();

    /**
     * 创建库存流水记录
     *
     * @param dto 库存流水DTO
     * @return 创建后的DTO
     */
    InventoryLogDTO create(InventoryLogDTO dto);
}
