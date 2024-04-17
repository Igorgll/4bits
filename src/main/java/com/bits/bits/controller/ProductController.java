package com.bits.bits.controller;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productService.findAllProducts();
        return ResponseEntity.ok(productsList);
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<Optional<ProductModel>> getProductById(@PathVariable long productId) {
        Optional<ProductModel> productsListById = productService.findProductById(productId);
        return ResponseEntity.ok(productsListById);
    }

    @GetMapping("/productName/{productName}")
    public ResponseEntity<List<ProductModel>> getAllProductsByName(@PathVariable String productName) {
        List<ProductModel> productsListByName = productService.findAllProductsByName(productName);
        return ResponseEntity.ok(productsListByName);
    }

    @GetMapping("/productImage/{productId}")
    public ResponseEntity<List<ProductImageProjection>> getAllImagesByProductId(@PathVariable long productId) {
        List<ProductImageProjection> productsImagesList = productService.findAllImagesByProductId(productId);
        return ResponseEntity.ok(productsImagesList);
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
