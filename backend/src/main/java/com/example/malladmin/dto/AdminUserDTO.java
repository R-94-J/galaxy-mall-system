package com.example.malladmin.dto;

import com.example.malladmin.entity.AdminUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台管理员用户DTO，用于前后端交互（不含密码）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO {

    private Long id;
    private String username;
    private String realName;
    private String roleName;
    private String phone;
    private String status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    /**
     * 从实体转换为DTO，排除密码字段
     *
     * @param user 管理员用户实体
     * @return AdminUserDTO
     */
    public static AdminUserDTO fromEntity(AdminUser user) {
        AdminUserDTO dto = new AdminUserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRealName(user.getRealName());
        dto.setRoleName(user.getRoleName());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus());
        dto.setLastLoginAt(user.getLastLoginAt());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
