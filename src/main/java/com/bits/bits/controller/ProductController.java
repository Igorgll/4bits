package com.bits.bits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bits.bits.model.ProductModel;
import com.bits.bits.repository.ProductRepository;
import com.bits.bits.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        if(!productsList.isEmpty()) {
            ResponseEntity.ok(productsList);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ProductModel> createProduct(@Valid @RequestBody ProductModel product){
        return productService.createProduct(product)
            .map(resp -> ResponseEntity.status(HttpStatus.OK)
            .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

}
