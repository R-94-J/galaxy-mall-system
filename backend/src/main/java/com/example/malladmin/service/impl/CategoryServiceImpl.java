package com.example.malladmin.service.impl;

import com.example.malladmin.dto.CategoryDTO;
import com.example.malladmin.entity.Category;
import com.example.malladmin.repository.CategoryRepository;
import com.example.malladmin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品分类服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> listAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDTO create(CategoryDTO dto) {
        Category entity = dto.toEntity();
        Category saved = categoryRepository.save(entity);
        log.info("创建分类: {}", saved.getName());
        return CategoryDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("分类不存在"));
        existing.setName(dto.getName());
        existing.setParentId(dto.getParentId());
        existing.setSortOrder(dto.getSortOrder());
        existing.setIcon(dto.getIcon());
        existing.setStatus(dto.getStatus());
        Category saved = categoryRepository.save(existing);
        log.info("更新分类: {}", saved.getName());
        return CategoryDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
        log.info("删除分类, ID: {}", id);
    }
}
