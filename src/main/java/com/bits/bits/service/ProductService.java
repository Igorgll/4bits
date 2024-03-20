package com.bits.bits.service;

import java.util.List;
import java.util.Optional;

import com.bits.bits.dto.ProductUpdateRequestDTO;
import com.bits.bits.model.ProductImagesModel;
import com.bits.bits.repository.ProductImagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.bits.model.ProductModel;
import com.bits.bits.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImagesRepository productImagesRepository;

    public Optional<ProductModel> createProduct(ProductModel product) {
        boolean findProduct = productRepository.existsByProductNameContainingIgnoreCase(product.getProductName());

        if (findProduct) {
            LOGGER.info("Product already registered");
            return Optional.empty();
        }

        ProductModel savedProduct = productRepository.save(product);

        List<ProductImagesModel> images = product.getProductImages();
        for (ProductImagesModel image : images) {
            image.setProduct(savedProduct);
        }

        productImagesRepository.saveAll(images);

        LOGGER.info("Product successfully created");
        return Optional.of(savedProduct);
    }

    public Optional<ProductModel> updateProduct(Long productId, ProductUpdateRequestDTO productDTO) {
        Optional<ProductModel> findProduct = productRepository.findById(productId);
        return findProduct.map(product -> {
            if (productDTO.getProductName() != null) {
                product.setProductName(productDTO.getProductName());
            }
            if (productDTO.getPrice() != null) {
                product.setPrice(productDTO.getPrice());
            }
            if (productDTO.getDescription() != null) {
                product.setDescription(productDTO.getDescription());
            }
            if (productDTO.getStorage() != null) {
                product.setStorage(productDTO.getStorage());
            }
            return productRepository.save(product);
        });
    }
}
