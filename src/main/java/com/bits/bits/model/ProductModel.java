package com.bits.bits.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    private int productID;

    @NotBlank(message = "Attribute productname cannot be null")
    private String productName;

    @NotBlank(message = "Attribute price cannot be null")
    private double price;
    
    @NotBlank(message = "Attribute picture cannot be null")
    private String picture;

    @NotBlank(message = "Attribute description cannot be null")
    private String description;

    @NotBlank(message = "Attribute expireDate cannot be null")
    private Date expireDate ;

    @NotBlank(message = "Attribute storage cannot be null")
    private int storage;

}
