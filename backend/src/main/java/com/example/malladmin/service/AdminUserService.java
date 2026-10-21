package com.example.malladmin.service;

import com.example.malladmin.dto.AdminUserCreateRequest;
import com.example.malladmin.dto.AdminUserDTO;

import java.util.List;

/**
 * 后台管理员用户服务接口
 */
public interface AdminUserService {

    /**
     * 查询所有管理员用户
     *
     * @return 管理员用户DTO列表
     */
    List<AdminUserDTO> listAll();

    /**
     * 创建管理员用户
     *
     * @param request 创建请求
     * @return 创建后的用户DTO
     */
    AdminUserDTO create(AdminUserCreateRequest request);

    /**
     * 更新管理员用户
     *
     * @param id      用户ID
     * @param request 更新请求
     * @return 更新后的用户DTO
     */
    AdminUserDTO update(Long id, AdminUserCreateRequest request);
}
