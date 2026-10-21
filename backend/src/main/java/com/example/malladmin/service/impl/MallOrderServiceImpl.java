package com.example.malladmin.service.impl;

import com.example.malladmin.dto.MallOrderDTO;
import com.example.malladmin.entity.MallOrder;
import com.example.malladmin.repository.MallOrderRepository;
import com.example.malladmin.service.MallOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商城订单管理服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MallOrderServiceImpl implements MallOrderService {

    private final MallOrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MallOrderDTO> listAll() {
        return orderRepository.findAll().stream()
                .map(MallOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MallOrderDTO detail(Long id) {
        MallOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("订单不存在"));
        return MallOrderDTO.fromEntity(order);
    }

    @Override
    @Transactional
    public MallOrderDTO changeStatus(Long id, String status) {
        MallOrder existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("订单不存在"));
        existing.setStatus(status);
        MallOrder saved = orderRepository.save(existing);
        log.info("订单状态变更: {} -> {}", saved.getOrderNo(), status);
        return MallOrderDTO.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MallOrderDTO> listByPhone(String phone) {
        return orderRepository.findByReceiverPhoneOrderByCreatedAtDesc(phone).stream()
                .map(MallOrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
