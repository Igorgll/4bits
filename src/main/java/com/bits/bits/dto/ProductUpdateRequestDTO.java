package com.bits.bits.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductUpdateRequestDTO {

    private String productName;
    private Double price;
    private String description;
    private Integer storage;
    // implementar quando tiver a função de adicionar imagens
    //private List<String> productImages;

}
