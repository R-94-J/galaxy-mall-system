package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.dto.CategoryDTO;
import com.example.malladmin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 分类管理控制器
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 查询所有分类
     */
    @GetMapping
    public ApiResponse<List<CategoryDTO>> list() {
        return ApiResponse.ok(categoryService.listAll());
    }

    /**
     * 创建分类
     */
    @PostMapping
    public ApiResponse<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) {
        return ApiResponse.ok(categoryService.create(dto));
    }

    /**
     * 更新分类
     */
    @PutMapping("/{id}")
    public ApiResponse<CategoryDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody CategoryDTO dto) {
        return ApiResponse.ok(categoryService.update(id, dto));
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.ok(true);
    }
}
