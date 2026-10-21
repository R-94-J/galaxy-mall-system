package com.example.malladmin.service.impl;

import com.example.malladmin.dto.CustomerDTO;
import com.example.malladmin.entity.Customer;
import com.example.malladmin.repository.CustomerRepository;
import com.example.malladmin.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商城会员管理服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> listAll() {
        return customerRepository.findAll().stream()
                .map(CustomerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerDTO create(CustomerDTO dto) {
        Customer entity = dto.toEntity();
        if (entity.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        Customer saved = customerRepository.save(entity);
        log.info("创建会员: {}", saved.getUsername());
        return CustomerDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public CustomerDTO update(Long id, CustomerDTO dto) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("会员不存在"));
        existing.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        existing.setNickname(dto.getNickname());
        existing.setPhone(dto.getPhone());
        existing.setLevelName(dto.getLevelName());
        existing.setPoints(dto.getPoints());
        existing.setTotalSpend(dto.getTotalSpend());
        existing.setStatus(dto.getStatus());
        Customer saved = customerRepository.save(existing);
        log.info("更新会员: {}", saved.getUsername());
        return CustomerDTO.fromEntity(saved);
    }
}
