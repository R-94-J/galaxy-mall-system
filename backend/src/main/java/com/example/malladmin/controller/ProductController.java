package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.dto.ProductDTO;
import com.example.malladmin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品管理控制器
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 查询所有商品
     */
    @GetMapping
    public ApiResponse<List<ProductDTO>> list() {
        return ApiResponse.ok(productService.listAll());
    }

    /**
     * 创建商品
     */
    @PostMapping
    public ApiResponse<ProductDTO> create(@Valid @RequestBody ProductDTO dto) {
        return ApiResponse.ok(productService.create(dto));
    }

    /**
     * 更新商品信息
     */
    @PutMapping("/{id}")
    public ApiResponse<ProductDTO> update(@PathVariable Long id,
                                          @Valid @RequestBody ProductDTO dto) {
        return ApiResponse.ok(productService.update(id, dto));
    }

    /**
     * 修改商品上下架状态
     */
    @PatchMapping("/{id}/status")
    public ApiResponse<ProductDTO> status(@PathVariable Long id,
                                          @RequestParam String status) {
        return ApiResponse.ok(productService.changeStatus(id, status));
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.ok(true);
    }
}
