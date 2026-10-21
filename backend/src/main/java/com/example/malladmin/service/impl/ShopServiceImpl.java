package com.example.malladmin.service.impl;

import com.example.malladmin.dto.CustomerDTO;
import com.example.malladmin.dto.MallOrderDTO;
import com.example.malladmin.dto.shop.ShopLoginRequest;
import com.example.malladmin.dto.shop.ShopOrderRequest;
import com.example.malladmin.dto.shop.ShopRegisterRequest;
import com.example.malladmin.entity.Customer;
import com.example.malladmin.entity.MallOrder;
import com.example.malladmin.entity.OrderItem;
import com.example.malladmin.repository.CustomerRepository;
import com.example.malladmin.repository.MallOrderRepository;
import com.example.malladmin.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 商城用户端服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final CustomerRepository customerRepository;
    private final MallOrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CustomerDTO register(ShopRegisterRequest request) {
        if (blank(request.getUsername()) || blank(request.getPassword()) || blank(request.getPhone())) {
            throw new IllegalArgumentException("账号、密码和手机号不能为空");
        }
        if (customerRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("账号已存在");
        }
        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("手机号已注册");
        }

        Customer customer = new Customer();
        customer.setUsername(request.getUsername());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setNickname(blank(request.getNickname()) ? request.getUsername() : request.getNickname());
        customer.setPhone(request.getPhone());
        customer.setLevelName("普通会员");
        customer.setPoints(0);
        customer.setTotalSpend(BigDecimal.ZERO);
        customer.setStatus("正常");
        customer.setRegisteredAt(LocalDateTime.now());

        Customer saved = customerRepository.save(customer);
        log.info("商城会员注册: {}", saved.getUsername());
        return CustomerDTO.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO login(ShopLoginRequest request) {
        if (blank(request.getAccount()) || blank(request.getPassword())) {
            throw new IllegalArgumentException("账号和密码不能为空");
        }
        Customer customer = customerRepository.findByUsername(request.getAccount())
                .or(() -> customerRepository.findByPhone(request.getAccount()))
                .orElse(null);
        if (customer == null || !passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("账号或密码错误");
        }
        if ("冻结".equals(customer.getStatus())) {
            throw new IllegalArgumentException("账号已冻结");
        }
        log.info("商城会员登录: {}", customer.getUsername());
        return CustomerDTO.fromEntity(customer);
    }

    @Override
    @Transactional
    public MallOrderDTO createOrder(ShopOrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("订单商品不能为空");
        }

        Customer customer = request.getCustomerId() == null
                ? null
                : customerRepository.findById(request.getCustomerId()).orElse(null);

        MallOrder order = new MallOrder();
        order.setOrderNo("SO" + System.currentTimeMillis());
        order.setCustomerName(customer != null ? customer.getNickname() : request.getCustomerName());
        order.setReceiverPhone(customer != null ? customer.getPhone() : request.getReceiverPhone());
        order.setAddress(blank(request.getAddress()) ? "用户在线支付订单" : request.getAddress());
        order.setPaymentMethod(blank(request.getPaymentMethod()) ? "在线支付" : request.getPaymentMethod());
        order.setAmount(request.getAmount() == null ? BigDecimal.ZERO : request.getAmount());
        order.setStatus("待发货");
        order.setCreatedAt(LocalDateTime.now());

        for (ShopOrderRequest.Item source : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductName(source.getProductName());
            item.setPrice(source.getPrice());
            item.setQuantity(source.getQuantity());
            item.setOrder(order);
            order.getItems().add(item);
        }

        MallOrder saved = orderRepository.save(order);

        if (customer != null) {
            BigDecimal currentSpend = customer.getTotalSpend() == null ? BigDecimal.ZERO : customer.getTotalSpend();
            customer.setTotalSpend(currentSpend.add(saved.getAmount()));
            customer.setPoints((customer.getPoints() == null ? 0 : customer.getPoints()) + saved.getAmount().intValue());
            customerRepository.save(customer);
        }

        log.info("创建商城订单: {}", saved.getOrderNo());
        return MallOrderDTO.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MallOrderDTO> listOrdersByPhone(String phone) {
        return orderRepository.findByReceiverPhoneOrderByCreatedAtDesc(phone).stream()
                .map(MallOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private boolean blank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
