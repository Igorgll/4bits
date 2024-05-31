package com.bits.bits.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
public class ProductUpdateRequestDTO {

    private String productName;
    private Double price;
    private String description;
    private List<MultipartFile> productImages;
    private double rating;
    private int storage;
    private boolean isActive;
}
