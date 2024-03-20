package com.bits.bits.repository;

import com.bits.bits.model.ProductImagesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImagesModel, Long> {
}