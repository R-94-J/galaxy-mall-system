package com.example.malladmin.service;

import java.util.Map;

/**
 * 数据大屏服务接口，提供仪表盘概览和图表数据
 */
public interface DashboardService {

    /**
     * 获取仪表盘概览数据（KPI指标、最新订单、库存预警）
     *
     * @return 概览数据Map
     */
    Map<String, Object> overview();

    /**
     * 获取图表数据（销售趋势、订单状态、品类排行、会员增长）
     *
     * @return 图表数据Map
     */
    Map<String, Object> charts();
}
