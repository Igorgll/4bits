package com.bits.bits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bits.bits.model.ProductModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    boolean existsByProductNameContainingIgnoreCase(String productName);

    List<ProductModel> findAllByProductNameContainingIgnoreCase(String productName);
}
