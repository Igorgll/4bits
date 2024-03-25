package com.bits.bits.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bits.bits.dto.ProductImageProjection;
import com.bits.bits.dto.ProductStatusDTO;
import com.bits.bits.dto.ProductUpdateRequestDTO;
import com.bits.bits.model.ProductModel;
import com.bits.bits.repository.ProductImagesRepository;
import com.bits.bits.repository.ProductRepository;
import com.bits.bits.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/listProducts")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        if (!productsList.isEmpty()) {
            return ResponseEntity.ok(productsList);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<List<ProductModel>> getImagesByProductId(@PathVariable long productId) {
        List<ProductModel> productsListById = productRepository.findAllById(Collections.singleton(productId));
        if (!productsListById.isEmpty()) {
            return ResponseEntity.ok(productsListById);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productName/{productName}")
    public ResponseEntity<List<ProductModel>> getAllProductsByName(@PathVariable String productName) {
        List<ProductModel> productsListByName = productRepository.findAllByProductNameContainingIgnoreCase(productName);
        if (!productsListByName.isEmpty()) {
            return ResponseEntity.ok(productsListByName);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productImage/{productId}")
    public ResponseEntity<List<ProductImageProjection>> getAllImagesByProductId(@PathVariable long productId) {
        List<ProductImageProjection> productsImagesList = productImagesRepository
                .findProductImagesByProductId(productId);
        if (!productsImagesList.isEmpty()) {
            return ResponseEntity.ok(productsImagesList);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ProductModel> createProduct(@Valid @RequestBody ProductModel product) {
        return productService.createProduct(product)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PatchMapping("/isProductActive/status")
    public ResponseEntity<ProductModel> isProductActive(@RequestBody ProductStatusDTO productStatus) {
        Long productId = productStatus.getProductId();
        boolean isActive = productStatus.isActive();
        return productService.changeProductStatus(productId, isActive)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequestDTO product) {
        return productService.updateProduct(productId, product)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

}
