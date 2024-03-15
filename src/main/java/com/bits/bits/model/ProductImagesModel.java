package com.bits.bits.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_product_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImagesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productImageID;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;
}
