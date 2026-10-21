package com.example.malladmin.service.impl;

import com.example.malladmin.dto.AdminUserCreateRequest;
import com.example.malladmin.dto.AdminUserDTO;
import com.example.malladmin.entity.AdminUser;
import com.example.malladmin.repository.AdminUserRepository;
import com.example.malladmin.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台管理员用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserDTO> listAll() {
        return userRepository.findAll().stream()
                .map(AdminUserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminUserDTO create(AdminUserCreateRequest request) {
        AdminUser entity = request.toEntity();
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setCreatedAt(LocalDateTime.now());
        AdminUser saved = userRepository.save(entity);
        log.info("创建管理员用户: {}", saved.getUsername());
        return AdminUserDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public AdminUserDTO update(Long id, AdminUserCreateRequest request) {
        AdminUser existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        existing.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        existing.setRealName(request.getRealName());
        existing.setRoleName(request.getRoleName());
        existing.setPhone(request.getPhone());
        existing.setStatus(request.getStatus());
        AdminUser saved = userRepository.save(existing);
        log.info("更新管理员用户: {}", saved.getUsername());
        return AdminUserDTO.fromEntity(saved);
    }
}
