package com.example.malladmin.repository;

import com.example.malladmin.entity.MallOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MallOrderRepository extends JpaRepository<MallOrder, Long> {
    long countByStatus(String status);
    List<MallOrder> findTop10ByOrderByCreatedAtDesc();
    List<MallOrder> findByReceiverPhoneOrderByCreatedAtDesc(String receiverPhone);
}
