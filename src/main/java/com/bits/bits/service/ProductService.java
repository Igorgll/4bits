package com.bits.bits.service;

import java.util.List;
import java.util.Optional;

import com.bits.bits.dto.ProductImageProjection;
import com.bits.bits.dto.ProductUpdateRequestDTO;
import com.bits.bits.exceptions.CannotAccessException;
import com.bits.bits.exceptions.NoContentException;
import com.bits.bits.model.ProductImagesModel;
import com.bits.bits.repository.ProductImagesRepository;
import jakarta.transaction.Transactional;
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

    @Transactional // todas as transações com o banco de dados precisam ter sucesso, se uma operação falhar, todas as operações relacionadas devem ser revertidas.
    public Optional<ProductModel> createProduct(ProductModel product) {
        boolean findProduct = productRepository.existsByProductNameContainingIgnoreCase(product.getProductName());

        if (findProduct) {
            LOGGER.info("Product already registered");
            throw new CannotAccessException();
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

    public Optional<ProductModel> changeProductStatus(Long productId, boolean isActive) {
        Optional<ProductModel> optProduct = productRepository.findById(productId);

        if (optProduct.isPresent()) {
            ProductModel product = optProduct.get();
            product.setActive(isActive);
            LOGGER.info("Product status altered successfully");
            return Optional.of(productRepository.save(product));
        }
        return Optional.empty();
    }

    public List<ProductModel> findAllProducts(){
        List<ProductModel> productsList = productRepository.findAll();
        if (productsList.isEmpty()){
            throw new NoContentException();
        }

        return productsList;
    }

    public Optional<ProductModel> findProductById(long productId){
        Optional<ProductModel> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new NoContentException();
        }
        return product;
    }

    public List<ProductModel> findAllProductsByName(String productName){
        List<ProductModel> productsList = productRepository.findAllByProductNameContainingIgnoreCase(productName);
        if (productsList.isEmpty()) {
            throw new NoContentException();
        }
        return productsList;
    }

    public List<ProductImageProjection> findAllImagesByProductId(long productId){
        List<ProductImageProjection> imagesList = productImagesRepository.findProductImagesByProductId(productId);
        if (imagesList.isEmpty()) {
            throw new NoContentException();
        }
        return imagesList;
    }


}
