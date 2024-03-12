package com.bits.bits.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productID;

    @NotBlank(message = "Attribute productname cannot be null")
    @Size(max = 200, message = "Attribute productname can have at maximum 200 characters")
    private String productName;

    @NotNull(message = "Attribute price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false)
    private double price;
    
    @NotEmpty(message = "Attribute picture cannot be null")
    @ElementCollection
    private List<String> pictures;

    @NotBlank(message = "Attribute description cannot be null")
    @Size(max = 2000, message = "Attribute productname can have at maximum 200 characters")
    private String description;

    @NotNull(message = "Attribute rating cannot be null")
    private double rating; 

    @NotNull
    private int storage;

}
