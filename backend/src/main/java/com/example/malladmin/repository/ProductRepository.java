package com.example.malladmin.repository;

import com.example.malladmin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    long countByStatus(String status);
    List<Product> findTop8ByOrderByStockAsc();
}

