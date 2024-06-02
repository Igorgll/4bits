package com.bits.bits.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @NotBlank(message = "Attribute productname cannot be null")
    @Size(max = 200, message = "Attribute productname can have at maximum 200 characters")
    @Column(name = "product_name")
    private String productName;

    @NotNull(message = "Attribute price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("product")
    private List<ProductImagesModel> productImages;

    @NotBlank(message = "Attribute description cannot be null")
    @Size(max = 2000, message = "Attribute productname can have at maximum 200 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Attribute rating cannot be null")
    @Column(name = "rating")
    private double rating; 

    @NotNull
    @Column(name = "storage")
    private int storage;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;
}
