package com.bits.bits.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.bits.bits.dto.ProductCreationDTO;
import com.bits.bits.dto.ProductImageProjection;
import com.bits.bits.dto.ProductUpdateRequestDTO;
import com.bits.bits.exceptions.CannotAccessException;
import com.bits.bits.exceptions.NoContentException;
import com.bits.bits.model.ProductImagesModel;
import com.bits.bits.repository.ProductImagesRepository;
import com.bits.bits.util.ImageCompressor;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.bits.model.ProductModel;
import com.bits.bits.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Transactional // todas as transações com o banco de dados precisam ter sucesso, se uma operação falhar, todas as operações relacionadas devem ser revertidas.
    public Optional<ProductModel> createProduct(ProductCreationDTO productDTO) {

        boolean findProduct = productRepository.existsByProductNameContainingIgnoreCase(productDTO.getProductName());

        if (findProduct) {
            LOGGER.info("Product already registered");
            throw new CannotAccessException();
        }

        ProductModel product = new ProductModel();
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setRating(productDTO.getRating());
        product.setStorage(productDTO.getStorage());
        product.setActive(true);

        List<MultipartFile> images = productDTO.getImages();
        List<ProductImagesModel> productImages = images.stream()
                .map(imageFile -> {
                    try {
                        byte[] compressedImageData = ImageCompressor.compressImage(imageFile.getBytes());
                        ProductImagesModel image = new ProductImagesModel();
                        image.setImageData(compressedImageData);
                        image.setProduct(product);
                        productImagesRepository.save(image);
                        return image;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read image file.");
                    }
                })
                .toList();

        product.setProductImages(productImages);
        ProductModel savedProduct = productRepository.save(product);

        LOGGER.info("Product successfully created");
        return Optional.of(savedProduct);
    }

    @Transactional
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
            if (productDTO.getRating() != 0) {
                product.setRating(productDTO.getRating());
            }
            if (productDTO.getStorage() != 0) {
                product.setStorage(productDTO.getStorage());
            }
            if (productDTO.getProductImages() != null && !productDTO.getProductImages().isEmpty()) {
                List<ProductImagesModel> productImages = productDTO.getProductImages().stream()
                        .map(imageFile -> {
                            try {
                                byte[] compressedImageData = ImageCompressor.compressImage(imageFile.getBytes());
                                ProductImagesModel image = new ProductImagesModel();
                                image.setImageData(compressedImageData);
                                image.setProduct(product);
                                return image;
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to read image file.");
                            }
                        })
                        .toList();
                product.getProductImages().clear();
                product.getProductImages().addAll(productImages);
            }
            product.setActive(productDTO.isActive());
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
