package com.bits.bits.repository;

import com.bits.bits.dto.ProductImageProjection;
import com.bits.bits.model.ProductImagesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImagesModel, Long> {
    @Query("SELECT p.productImageID AS productImageID, p.imageData AS imageData FROM ProductImagesModel p WHERE p.product.productId = :productId")
    List<ProductImageProjection> findProductImagesByProductId(long productId);
}