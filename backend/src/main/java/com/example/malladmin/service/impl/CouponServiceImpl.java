package com.example.malladmin.service.impl;

import com.example.malladmin.dto.CouponDTO;
import com.example.malladmin.entity.Coupon;
import com.example.malladmin.repository.CouponRepository;
import com.example.malladmin.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券管理服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CouponDTO> listAll() {
        return couponRepository.findAll().stream()
                .map(CouponDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CouponDTO create(CouponDTO dto) {
        Coupon entity = dto.toEntity();
        Coupon saved = couponRepository.save(entity);
        log.info("创建优惠券: {}", saved.getName());
        return CouponDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public CouponDTO update(Long id, CouponDTO dto) {
        Coupon existing = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("优惠券不存在"));
        existing.setName(dto.getName());
        existing.setThresholdAmount(dto.getThresholdAmount());
        existing.setDiscountAmount(dto.getDiscountAmount());
        existing.setIssuedCount(dto.getIssuedCount());
        existing.setUsedCount(dto.getUsedCount());
        existing.setStatus(dto.getStatus());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        Coupon saved = couponRepository.save(existing);
        log.info("更新优惠券: {}", saved.getName());
        return CouponDTO.fromEntity(saved);
    }
}
