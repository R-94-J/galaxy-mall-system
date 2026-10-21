package com.example.malladmin.controller;

import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.dto.CustomerDTO;
import com.example.malladmin.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员管理控制器
 */
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 查询所有会员
     */
    @GetMapping
    public ApiResponse<List<CustomerDTO>> list() {
        return ApiResponse.ok(customerService.listAll());
    }

    /**
     * 创建会员
     */
    @PostMapping
    public ApiResponse<CustomerDTO> create(@Valid @RequestBody CustomerDTO dto) {
        return ApiResponse.ok(customerService.create(dto));
    }

    /**
     * 更新会员信息
     */
    @PutMapping("/{id}")
    public ApiResponse<CustomerDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody CustomerDTO dto) {
        return ApiResponse.ok(customerService.update(id, dto));
    }
}
