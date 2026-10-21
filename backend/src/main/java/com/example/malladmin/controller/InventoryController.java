package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.dto.InventoryLogDTO;
import com.example.malladmin.service.InventoryLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 库存管理控制器
 */
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryLogService inventoryLogService;

    /**
     * 查询所有库存流水
     */
    @GetMapping
    public ApiResponse<List<InventoryLogDTO>> list() {
        return ApiResponse.ok(inventoryLogService.listAll());
    }

    /**
     * 创建库存流水记录
     */
    @PostMapping
    public ApiResponse<InventoryLogDTO> create(@Valid @RequestBody InventoryLogDTO dto) {
        return ApiResponse.ok(inventoryLogService.create(dto));
    }
}
