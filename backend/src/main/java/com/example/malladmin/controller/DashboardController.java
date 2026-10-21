package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 数据大屏控制器，提供仪表盘概览和图表数据
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 获取仪表盘概览数据
     */
    @GetMapping("/overview")
    public ApiResponse<Map<String, Object>> overview() {
        return ApiResponse.ok(dashboardService.overview());
    }

    /**
     * 获取图表数据
     */
    @GetMapping("/charts")
    public ApiResponse<Map<String, Object>> charts() {
        return ApiResponse.ok(dashboardService.charts());
    }
}
