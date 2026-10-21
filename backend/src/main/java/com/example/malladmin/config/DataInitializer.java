package com.example.malladmin.config;

import com.example.malladmin.entity.*;
import com.example.malladmin.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 数据初始化配置类，首次启动时插入演示数据
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData(AdminUserRepository users, CategoryRepository categories,
                               ProductRepository products, CustomerRepository customers,
                               MallOrderRepository orders, InventoryLogRepository inventoryLogs,
                               CouponRepository coupons) {
        return args -> {
            if (users.count() == 0) {
                AdminUser admin = new AdminUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setRealName("系统管理员");
                admin.setRoleName("超级管理员");
                admin.setPhone("13800000000");
                admin.setStatus("启用");
                admin.setCreatedAt(LocalDateTime.now().minusDays(30));
                users.save(admin);
                log.info("初始化管理员账号: admin");
            }

            if (categories.count() == 0) {
                categories.saveAll(Arrays.asList(
                        category("数码家电", 0L, 1, "Monitor"),
                        category("美妆个护", 0L, 2, "Sparkles"),
                        category("服饰鞋包", 0L, 3, "Shirt"),
                        category("食品生鲜", 0L, 4, "Apple")
                ));
                log.info("初始化商品分类: 4条");
            }

            if (products.count() == 0) {
                products.saveAll(Arrays.asList(
                        product("SKU-1001", "Aurora X1 智能手表", "数码家电", "699", "420", 86, 532, "上架"),
                        product("SKU-1002", "ProSound 降噪耳机", "数码家电", "899", "520", 24, 831, "上架"),
                        product("SKU-2001", "山茶花修护精华", "美妆个护", "239", "98", 120, 942, "上架"),
                        product("SKU-2002", "氨基酸沐浴套装", "美妆个护", "129", "58", 18, 611, "上架"),
                        product("SKU-3001", "都市通勤轻羽外套", "服饰鞋包", "359", "160", 44, 316, "上架"),
                        product("SKU-3002", "云感运动鞋", "服饰鞋包", "499", "230", 29, 463, "上架"),
                        product("SKU-4001", "低温烘焙坚果礼盒", "食品生鲜", "169", "79", 220, 1221, "上架"),
                        product("SKU-4002", "有机蓝莓鲜果盒", "食品生鲜", "89", "42", 12, 286, "下架")
                ));
                log.info("初始化商品: 8条");
            }

            if (customers.count() == 0) {
                customers.saveAll(Arrays.asList(
                        customer("lin", "林小满", "13910000001", "黄金会员", 3280, "12980.50", "正常", 120),
                        customer("chen", "陈帆", "13910000002", "铂金会员", 8290, "32890.00", "正常", 95),
                        customer("zhou", "周北辰", "13910000003", "普通会员", 620, "2390.00", "正常", 30),
                        customer("su", "苏念", "13910000004", "钻石会员", 14200, "69880.00", "正常", 160),
                        customer("xu", "许祺", "13910000005", "黄金会员", 2600, "9850.00", "冻结", 48)
                ));
                log.info("初始化会员: 5条");
            }

            if (orders.count() == 0) {
                List<String> customerNames = Arrays.asList("林小满", "陈帆", "周北辰", "苏念", "许祺");
                List<String> productNames = Arrays.asList("Aurora X1 智能手表", "ProSound 降噪耳机", "山茶花修护精华", "低温烘焙坚果礼盒");
                List<String> statusList = Arrays.asList("待付款", "待发货", "已发货", "已完成", "售后中");
                List<String> payMethods = Arrays.asList("支付宝", "微信支付", "银行卡");

                for (int i = 1; i <= 18; i++) {
                    MallOrder order = new MallOrder();
                    order.setOrderNo("MO20260703" + String.format("%04d", i));
                    order.setCustomerName(customerNames.get(i % 5));
                    order.setReceiverPhone("1391000" + String.format("%04d", i));
                    order.setAddress("上海市浦东新区云栖路 " + (80 + i) + " 号");
                    order.setAmount(BigDecimal.valueOf(260 + i * 57L));
                    order.setPaymentMethod(payMethods.get(i % 3));
                    order.setStatus(statusList.get(i % 5));
                    order.setCreatedAt(LocalDateTime.now().minusDays(18 - i));

                    OrderItem item = new OrderItem();
                    item.setProductName(productNames.get(i % 4));
                    item.setPrice(BigDecimal.valueOf(99 + i * 20L));
                    item.setQuantity(i % 3 + 1);
                    item.setOrder(order);
                    order.getItems().add(item);

                    orders.save(order);
                }
                log.info("初始化订单: 18条");
            }

            if (inventoryLogs.count() == 0) {
                inventoryLogs.saveAll(Arrays.asList(
                        log("ProSound 降噪耳机", "出库", 32, 24, "仓库主管", "直播活动订单扣减"),
                        log("氨基酸沐浴套装", "预警", 0, 18, "系统", "库存低于安全阈值"),
                        log("有机蓝莓鲜果盒", "出库", 46, 12, "仓库主管", "当日生鲜配送"),
                        log("低温烘焙坚果礼盒", "入库", 160, 220, "采购专员", "节日礼盒补货")
                ));
                log.info("初始化库存流水: 4条");
            }

            if (coupons.count() == 0) {
                coupons.saveAll(Arrays.asList(
                        coupon("新人满99减20", "99", "20", 5000, 1890, "进行中"),
                        coupon("夏季数码满799减80", "799", "80", 1200, 326, "进行中"),
                        coupon("会员专享满199减35", "199", "35", 2600, 932, "未开始")
                ));
                log.info("初始化优惠券: 3条");
            }
        };
    }

    private Category category(String name, Long parentId, Integer sort, String icon) {
        Category category = new Category();
        category.setName(name);
        category.setParentId(parentId);
        category.setSortOrder(sort);
        category.setIcon(icon);
        category.setStatus("启用");
        return category;
    }

    private Product product(String sku, String name, String categoryName, String price,
                            String cost, Integer stock, Integer sales, String status) {
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);
        product.setCategoryName(categoryName);
        product.setPrice(new BigDecimal(price));
        product.setCostPrice(new BigDecimal(cost));
        product.setStock(stock);
        product.setSales(sales);
        product.setStatus(status);
        product.setCoverUrl("https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=400&q=80");
        product.setCreatedAt(LocalDateTime.now().minusDays(sales % 90));
        return product;
    }

    private Customer customer(String username, String nickname, String phone, String level,
                              Integer points, String spend, String status, int days) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(passwordEncoder.encode("123456"));
        customer.setNickname(nickname);
        customer.setPhone(phone);
        customer.setLevelName(level);
        customer.setPoints(points);
        customer.setTotalSpend(new BigDecimal(spend));
        customer.setStatus(status);
        customer.setRegisteredAt(LocalDateTime.now().minusDays(days));
        return customer;
    }

    private InventoryLog log(String productName, String type, Integer quantity,
                             Integer balance, String operator, String remark) {
        InventoryLog log = new InventoryLog();
        log.setProductName(productName);
        log.setType(type);
        log.setQuantity(quantity);
        log.setBalance(balance);
        log.setOperatorName(operator);
        log.setRemark(remark);
        log.setCreatedAt(LocalDateTime.now().minusDays(quantity % 8 + 1));
        return log;
    }

    private Coupon coupon(String name, String threshold, String discount,
                          Integer issued, Integer used, String status) {
        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setThresholdAmount(new BigDecimal(threshold));
        coupon.setDiscountAmount(new BigDecimal(discount));
        coupon.setIssuedCount(issued);
        coupon.setUsedCount(used);
        coupon.setStatus(status);
        coupon.setStartDate(LocalDate.now().minusDays(5));
        coupon.setEndDate(LocalDate.now().plusDays(30));
        return coupon;
    }
}
