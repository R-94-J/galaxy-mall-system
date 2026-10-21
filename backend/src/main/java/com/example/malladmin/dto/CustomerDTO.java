package com.example.malladmin.dto;

import com.example.malladmin.entity.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商城会员DTO，用于前后端交互（密码仅写入时使用）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    /** 登录账号 */
    @NotBlank(message = "账号不能为空")
    private String username;

    /** 登录密码（仅接收请求，不返回前端） */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /** 会员昵称 */
    private String nickname;

    /** 手机号 */
    private String phone;

    /** 会员等级名称 */
    private String levelName;

    /** 积分 */
    private Integer points;

    /** 累计消费金额 */
    private BigDecimal totalSpend;

    /** 状态：正常/冻结 */
    private String status;

    /** 注册时间 */
    private LocalDateTime registeredAt;

    /**
     * 从实体转换为DTO，排除密码
     *
     * @param customer 会员实体
     * @return CustomerDTO
     */
    public static CustomerDTO fromEntity(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setUsername(customer.getUsername());
        dto.setNickname(customer.getNickname());
        dto.setPhone(customer.getPhone());
        dto.setLevelName(customer.getLevelName());
        dto.setPoints(customer.getPoints());
        dto.setTotalSpend(customer.getTotalSpend());
        dto.setStatus(customer.getStatus());
        dto.setRegisteredAt(customer.getRegisteredAt());
        return dto;
    }

    /**
     * 将DTO转换为实体对象
     *
     * @return Customer实体
     */
    public Customer toEntity() {
        Customer entity = new Customer();
        entity.setId(this.id);
        entity.setUsername(this.username);
        entity.setPassword(this.password);
        entity.setNickname(this.nickname);
        entity.setPhone(this.phone);
        entity.setLevelName(this.levelName);
        entity.setPoints(this.points);
        entity.setTotalSpend(this.totalSpend);
        entity.setStatus(this.status);
        entity.setRegisteredAt(this.registeredAt);
        return entity;
    }
}
