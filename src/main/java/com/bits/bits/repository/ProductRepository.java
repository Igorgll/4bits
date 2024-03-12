package com.bits.bits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bits.bits.model.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
