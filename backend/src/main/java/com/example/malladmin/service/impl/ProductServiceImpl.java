package com.example.malladmin.service.impl;

import com.example.malladmin.dto.ProductDTO;
import com.example.malladmin.entity.Product;
import com.example.malladmin.repository.ProductRepository;
import com.example.malladmin.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品管理服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> listAll() {
        return productRepository.findAll().stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO dto) {
        Product entity = dto.toEntity();
        entity.setCreatedAt(LocalDateTime.now());
        Product saved = productRepository.save(entity);
        log.info("创建商品: {}", saved.getName());
        return ProductDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));
        existing.setSku(dto.getSku());
        existing.setName(dto.getName());
        existing.setCategoryName(dto.getCategoryName());
        existing.setPrice(dto.getPrice());
        existing.setCostPrice(dto.getCostPrice());
        existing.setStock(dto.getStock());
        existing.setSales(dto.getSales());
        existing.setStatus(dto.getStatus());
        existing.setCoverUrl(dto.getCoverUrl());
        Product saved = productRepository.save(existing);
        log.info("更新商品: {}", saved.getName());
        return ProductDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public ProductDTO changeStatus(Long id, String status) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在"));
        existing.setStatus(status);
        Product saved = productRepository.save(existing);
        log.info("商品状态变更: {} -> {}", saved.getName(), status);
        return ProductDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
        log.info("删除商品, ID: {}", id);
    }
}
