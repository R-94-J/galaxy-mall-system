package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.dto.MallOrderDTO;
import com.example.malladmin.service.MallOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单管理控制器
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final MallOrderService mallOrderService;

    /**
     * 查询所有订单
     */
    @GetMapping
    public ApiResponse<List<MallOrderDTO>> list() {
        return ApiResponse.ok(mallOrderService.listAll());
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/{id}")
    public ApiResponse<MallOrderDTO> detail(@PathVariable Long id) {
        return ApiResponse.ok(mallOrderService.detail(id));
    }

    /**
     * 修改订单状态
     */
    @PatchMapping("/{id}/status")
    public ApiResponse<MallOrderDTO> status(@PathVariable Long id,
                                            @RequestParam String status) {
        return ApiResponse.ok(mallOrderService.changeStatus(id, status));
    }
}
