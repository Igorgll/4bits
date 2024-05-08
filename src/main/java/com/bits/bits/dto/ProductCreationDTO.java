package com.bits.bits.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductCreationDTO {

    private String productName;

    private double price;

    private List<MultipartFile> images;

    private String description;

    private double rating;

    private int storage;

    private boolean isActive;
}
