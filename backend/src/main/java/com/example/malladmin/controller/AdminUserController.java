package com.example.malladmin.controller;

import com.example.malladmin.dto.AdminUserCreateRequest;
import com.example.malladmin.dto.AdminUserDTO;
import com.example.malladmin.dto.ApiResponse;
import com.example.malladmin.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理员用户控制器
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 查询所有管理员用户
     */
    @GetMapping
    public ApiResponse<List<AdminUserDTO>> list() {
        return ApiResponse.ok(adminUserService.listAll());
    }

    /**
     * 创建管理员用户
     */
    @PostMapping
    public ApiResponse<AdminUserDTO> create(@Valid @RequestBody AdminUserCreateRequest request) {
        return ApiResponse.ok(adminUserService.create(request));
    }

    /**
     * 更新管理员用户
     */
    @PutMapping("/{id}")
    public ApiResponse<AdminUserDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody AdminUserCreateRequest request) {
        return ApiResponse.ok(adminUserService.update(id, request));
    }
}
