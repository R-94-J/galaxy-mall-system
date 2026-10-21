package com.example.malladmin.service.impl;

import com.example.malladmin.dto.InventoryLogDTO;
import com.example.malladmin.entity.InventoryLog;
import com.example.malladmin.repository.InventoryLogRepository;
import com.example.malladmin.service.InventoryLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存管理服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryLogServiceImpl implements InventoryLogService {

    private final InventoryLogRepository inventoryLogRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryLogDTO> listAll() {
        return inventoryLogRepository.findAll().stream()
                .map(InventoryLogDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InventoryLogDTO create(InventoryLogDTO dto) {
        InventoryLog entity = dto.toEntity();
        entity.setCreatedAt(LocalDateTime.now());
        InventoryLog saved = inventoryLogRepository.save(entity);
        log.info("创建库存流水: {} {}", saved.getProductName(), saved.getType());
        return InventoryLogDTO.fromEntity(saved);
    }
}
