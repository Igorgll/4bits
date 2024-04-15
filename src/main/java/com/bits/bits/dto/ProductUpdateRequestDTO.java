package com.bits.bits.dto;

import com.bits.bits.model.ProductImagesModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ProductUpdateRequestDTO {

    private long productId;
    private String productName;
    private Double price;
    private List<ProductImagesModel> productImages;
    private String description;
    private double rating;
    private int storage;
    private boolean isActive;

    // implementar quando tiver a função de adicionar imagens
    // private List<String> productImages;

}
