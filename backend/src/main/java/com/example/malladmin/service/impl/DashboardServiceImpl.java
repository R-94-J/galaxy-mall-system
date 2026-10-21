package com.example.malladmin.service.impl;

import com.example.malladmin.entity.MallOrder;
import com.example.malladmin.entity.Product;
import com.example.malladmin.repository.CustomerRepository;
import com.example.malladmin.repository.MallOrderRepository;
import com.example.malladmin.repository.ProductRepository;
import com.example.malladmin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据大屏服务实现类，提供仪表盘概览和图表数据
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;
    private final MallOrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> overview() {
        List<MallOrder> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        BigDecimal totalSales = orders.stream()
                .map(MallOrder::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalSales", totalSales);
        data.put("orderCount", orders.size());
        data.put("productCount", products.size());
        data.put("customerCount", customerRepository.count());
        data.put("onlineProducts", products.stream()
                .filter(p -> "上架".equals(p.getStatus()))
                .count());
        data.put("pendingOrders", orders.stream()
                .filter(o -> "待发货".equals(o.getStatus()))
                .count());
        data.put("lowStockCount", products.stream()
                .filter(p -> p.getStock() != null && p.getStock() < 30)
                .count());
        data.put("recentOrders", orderRepository.findTop10ByOrderByCreatedAtDesc());
        data.put("lowStockProducts", productRepository.findTop8ByOrderByStockAsc());
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> charts() {
        List<MallOrder> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        Map<String, BigDecimal> salesByDay = orders.stream()
                .filter(o -> o.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        o -> o.getCreatedAt().format(formatter),
                        LinkedHashMap::new,
                        Collectors.reducing(BigDecimal.ZERO, MallOrder::getAmount, BigDecimal::add)));

        Map<String, Long> statusCount = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getStatus() == null ? "未知" : o.getStatus(),
                        LinkedHashMap::new,
                        Collectors.counting()));

        Map<String, Integer> categorySales = products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCategoryName() == null ? "未分类" : p.getCategoryName(),
                        LinkedHashMap::new,
                        Collectors.summingInt(p -> Optional.ofNullable(p.getSales()).orElse(0))));

        Map<String, Object> memberGrowth = new LinkedHashMap<>();
        memberGrowth.put("labels", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
        memberGrowth.put("values", Arrays.asList(120, 180, 260, 360, 510, 690));

        Map<String, Object> salesTrend = new LinkedHashMap<>();
        salesTrend.put("labels", salesByDay.keySet());
        salesTrend.put("values", salesByDay.values());

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("salesTrend", salesTrend);
        data.put("orderStatus", statusCount);
        data.put("categorySales", categorySales);
        data.put("memberGrowth", memberGrowth);
        return data;
    }
}
